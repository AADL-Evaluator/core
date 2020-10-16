package org.osate.aadl.evaluator.unit;

import static org.osate.aadl.evaluator.unit.UnitUtils.getValueAndUnit;

public class TimeUtils extends UnitUtils
{
    private static final String[] UNITS_TIME = { "us" , "ms" , "s"  , "m" , "h" , "d" };
    private static final int[] UNITS_FACTOR  = { 1000 , 1000 , 1000 , 60  , 60  , 24  };
    
    private TimeUtils()
    {
        // faz nada
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