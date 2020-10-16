package org.osate.aadl.evaluator.unit;

public class OriginalUnitUtils 
{
    private static final String UNITS = " KMGT";
    
    private static final String[] UNITS_TIME = { "ms" , "s"  , "m" , "h" , "d" };
    private static final int[] UNITS_FACTOR  = {    1 , 1000 , 60  , 60  , 24  };
    
    private static final int BIT_BYTE = 8;
    private static final int DISTANCE_DECIMAL = 1000;
    private static final int DISTANCE_BINARY  = 1024;
    
    protected OriginalUnitUtils()
    {
        // faz nada
    }
    
    public static double getValue( String value )
    {
        return Double.parseDouble( 
            getValueAndUnit( value )[0] 
        );
    }
    
    public static double getTimeValue( String value , String unit )
    {
        return Double.parseDouble( 
            getValueAndUnit( timeConvert( value , unit ) )[0] 
        );
    }
    
    public static double getSizeValue( String value , String unit )
    {
        return Double.parseDouble( 
            getValueAndUnit( sizeConvert( value , unit ) )[0] 
        );
    }
    
    public static double getBitspsValue( String value , String unit )
    {
        return Double.parseDouble( 
            getValueAndUnit( bitsPorSecondConvert( value , unit ) )[0] 
        );
    }
    
    // -------------------------------------- //
    // -------------------------------------- // CONVERT
    // -------------------------------------- //
    
    /**
     * convert ms to s, vice versa.
     * 
     * 1000 ms , s
     * 
     * 
     * @param valueWithUnit
     * @param unit
     * @return 
     */
    public static String timeConvert( String valueWithUnit , String unit )
    {
        String[] parts = getValueAndUnit( valueWithUnit );
        
        int pos1 = getTimeUnitPosition( parts[1] );
        int pos2 = getTimeUnitPosition( unit     );
        
        if( pos1 == pos2 )
        {
            return valueWithUnit;
        }
        
        double value = Double.parseDouble( parts[0] );
        
        if( pos1 < pos2 )
        {
            int factor = 1;
            
            for( int i = pos1 ; i < pos2 ; i++ )
            {
                factor = factor * UNITS_FACTOR[ i + 1 ];
            }
            
            value = value / factor;
        }
        else
        {
            int factor = 1;
            
            for( int i = pos2 ; i < pos1 ; i++ )
            {
                factor = factor * UNITS_FACTOR[ i + 1 ];
            }
            
            value = value * factor;
        }
        
        return value + " " + unit;
    }
    
    public static String sizeConvert( String valueWithUnit , String unit )
    {
        return convert( 
            valueWithUnit , 
            unit , 
            DISTANCE_DECIMAL 
        );
    }
    
    public static String bitsPorSecondConvert( String valueWithUnit , String unit )
    {
        return convert( 
            valueWithUnit , 
            unit , 
            DISTANCE_DECIMAL 
        );
    }
    
    private static String convert( String valueWithUnit , String unit , int distanceSize )
    {
        String[] parts = getValueAndUnit( valueWithUnit );
        
        // bitsps, Kbps, Mbps, Gbps
        // KBytesps
        
        //System.out.println( "---" );
        
        boolean isBits1 = isBitUnit( parts[1] );
        boolean isBits2 = isBitUnit( unit );
        
        //System.out.println( "isBits1: " + isBits1 );
        //System.out.println( "isBits2: " + isBits2 );
        
        int pos1 = UNITS.indexOf( parts[1].toUpperCase().charAt( 0 ) );
        int pos2 = UNITS.indexOf( unit.toUpperCase().charAt( 0 ) );
        
        //System.out.println( "pos1: " + pos1 );
        //System.out.println( "pos2: " + pos2 );
        
        if( pos1 == -1 )
        {
            pos1 = 0;
        }
        
        if( pos2 == -1 )
        {
            pos2 = 0;
        }
        
        if( isBits1 == isBits2 && pos1 == pos2 )
        {
            return valueWithUnit;
        }
        
        double value = Double.parseDouble( parts[0] );
        //System.out.println( "value: " + value );
        
        if( isBits1 && !isBits2 )
        {
            // convert bit to byte
            value /= BIT_BYTE;
        }
        else if( !isBits1 && isBits2 )
        {
            // convert byte to bit
            value *= BIT_BYTE;
        }
        
        //System.out.println( "value: " + value );
        
        if( pos1 < pos2 )
        {
            // ex., Kbps to Mbps
            value = value / Math.pow( distanceSize , pos2 - pos1 );
            //System.out.println( "divisor: " + Math.pow( distanceSize , pos1 - pos2 ) );
        }
        else if( pos1 > pos2 )
        {
            // ex., Mbps to Kbps
            value = value * Math.pow( distanceSize , pos1 - pos2 );
            //System.out.println( "multiplicador: " + Math.pow( distanceSize , pos1 - pos2 ) );
        }
        
        //System.out.println( "value: " + value );
        
        return value + " " + unit;
    }
    
    private static boolean isBitUnit( String unit )
    {
        if( unit.length() == 2 )
        {
            return unit.charAt( 1 ) == 'b';
        }
        
        return unit.contains( "bps" )
            || unit.contains( "bitsps" )
            || unit.contains( "bit" );
    }
    
    public static String[] getValueAndUnit( String value )
    {
        String[] parts = new String[2];
        parts[0] = "";
        parts[1] = "";
        
        if( value == null 
            || value.trim().isEmpty() )
        {
            parts[0] = "0";
            return parts;
        }
        
        for( char c : value.toCharArray() )
        {
            if( Character.isDigit( c ) 
                || c == '.' )
            {
                parts[0] += c;
            }
            else if( c != ' ' )
            {
                parts[1] += c;
            }
        }
        
        return parts;
    }
    
    private static int getTimeUnitPosition( String unit )
    {
        for( int i = 0 ; i < UNITS_TIME.length; i++ )
        {
            String u = UNITS_TIME[ i ];
            
            if( u.equalsIgnoreCase( unit ) )
            {
                return i;
            }
        }
        
        return -1;
    }
    
}