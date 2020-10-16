package org.osate.aadl.evaluator.unit;

public class PriceUtils extends UnitUtils
{
    private static final String[] UNITS_PRICE = { "cent" , "Dollar" };
    private static final int[] UNITS_FACTOR  = {  0      , 100      };
    
    private PriceUtils()
    {
        // faz nada
    }
    
    public static String sum( String v1 , String v2 , String unit )
    {
        return (getValue( v1 , unit ) + getValue( v2 , unit )) 
            + " " 
            + unit;
    }
    
    public static double getValue( String value , String unit )
    {
        if( isEmpty( value ) )
        {
            return 0;
        }
        
        String valueStr = getValueAndUnit( convert( value , unit ) )[0];
        if( isEmpty( valueStr ) )
        {
            return 0;
        }
        
        return Double.parseDouble( valueStr );
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
    public static String convert( String valueWithUnit , String unit )
    {
        if( !hasUnits( valueWithUnit , UNITS_PRICE ) )
        {
            return valueWithUnit;
        }
        
        String[] parts = getValueAndUnit( valueWithUnit );
        
        int pos1 = getPosition( parts[1] );
        int pos2 = getPosition( unit     );
        
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
    
    private static int getPosition( String unit )
    {
        for( int i = 0 ; i < UNITS_PRICE.length; i++ )
        {
            String u = UNITS_PRICE[ i ];
            
            if( u.equalsIgnoreCase( unit ) )
            {
                return i;
            }
        }
        
        return -1;
    }
    
}