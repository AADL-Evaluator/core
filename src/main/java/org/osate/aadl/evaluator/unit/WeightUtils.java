package org.osate.aadl.evaluator.unit;

import static org.osate.aadl.evaluator.unit.UnitUtils.convert;
import static org.osate.aadl.evaluator.unit.UnitUtils.getValueAndUnit;

public class WeightUtils extends UnitUtils
{
    private static final String[] WEIGHT_UNITS = { "µg"  , "mg" , "g" , "kg" , "t" };
    private static final int WEIGHT_FACTOR = 1000;
    
    private WeightUtils()
    {
        // do nothing
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
    
    public static String convert( String valueWithUnit , String unit )
    {
        return convert( 
            valueWithUnit , 
            unit , 
            WEIGHT_UNITS , 
            WEIGHT_FACTOR 
        );
    }
    
}