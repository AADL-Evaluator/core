package org.osate.aadl.evaluator.unit;

import java.util.Arrays;
import java.util.Comparator;

public class UnitUtils 
{
    public static final String UNITS = " KMGT";
    public static final int BIT_BYTE = 8;
    public static final int DISTANCE_DECIMAL = 1000;
    public static final int DISTANCE_BINARY  = 1024;
    
    protected UnitUtils()
    {
        // faz nada
    }
    
    public static double getValue( String value )
    {
        if( isEmpty( value ) )
        {
            return 0;
        }
        
        String valueStr = getValueAndUnit( value )[0];
        if( isEmpty( valueStr ) )
        {
            return 0;
        }
        
        return Double.parseDouble( valueStr );
    }
    
    protected static String convert( String valueWithUnit , String unit , String[] units , int base , int[] factor )
    {
        if( !hasUnits( valueWithUnit , units ) )
        {
            return valueWithUnit;
        }
        
        String[] parts = getValueAndUnit( valueWithUnit );
        
        int pos1 = binarySearch( units , parts[1] , true );
        int pos2 = binarySearch( units , unit     , true );
        
        if( pos1 == -1 ) pos1 = 0;
        if( pos2 == -1 ) pos2 = 0;
        
        if( pos1 == pos2 )
        {
            return valueWithUnit;
        }
        
        double value = Double.parseDouble( parts[0] );
        
        if( pos1 < pos2 )
        {
            value = value / Math.pow( base , factor[ pos2 ] - factor[ pos1 ] );
        }
        else if( pos1 > pos2 )
        {
            value = value * Math.pow( base , factor[ pos1 ] - factor[ pos2 ] );
        }
        
        return value + " " + unit;
    }
    
    protected static String convert( String valueWithUnit , String unit , String[] units , int factor )
    {
        if( !hasUnits( valueWithUnit , units ) )
        {
            return valueWithUnit;
        }
        
        String[] parts = getValueAndUnit( valueWithUnit );
        
        int pos1 = binarySearch( units , parts[1] , false );
        int pos2 = binarySearch( units , unit     , false );
        
        if( pos1 == -1 ) pos1 = 0;
        if( pos2 == -1 ) pos2 = 0;
        
        if( pos1 == pos2 )
        {
            return valueWithUnit;
        }
        
        double value = Double.parseDouble( parts[0] );
        
        if( pos1 < pos2 )
        {
            value = value / Math.pow( factor , pos2 - pos1 );
        }
        else if( pos1 > pos2 )
        {
            value = value * Math.pow( factor , pos1 - pos2 );
        }
        
        return value + " " + unit;
    }
    
    protected static int binarySearch( final String[] units , final String unit , boolean casesensitive )
    {
        return Arrays.binarySearch( units , 0 , units.length , unit , new Comparator<String>(){
            @Override
            public int compare( String o1 , String o2 ) {
                return casesensitive 
                    ? o1.compareTo( o2 ) 
                    : o1.compareToIgnoreCase( o2 );
            }
        });
    }
    
    protected static String convert( String valueWithUnit , String unit , int distanceSize )
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
            if( parts[1].trim().isEmpty() 
                && (Character.isDigit( c ) || c == '.' || c == 'E' || c == '-') )
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
    
    public static boolean isEmpty( String value )
    {
        return value == null || value.trim().isEmpty();
    }
    
    public static boolean hasUnits( String value , String[] units )
    {
        for( String unit : units )
        {
            if( value.toUpperCase().contains( unit.toUpperCase() ) )
            {
                return true;
            }
        }
        
        return false;
    }
    
}