package org.osate.aadl.evaluator.unit;

import static org.osate.aadl.evaluator.unit.UnitUtils.getValueAndUnit;

public class MipsUtils extends UnitUtils
{
    
    protected MipsUtils()
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
    
    public static String convert( String valueWithUnit , String unit )
    {
        return UnitUtils.convert( 
            valueWithUnit , 
            unit , 
            DISTANCE_DECIMAL 
        );
    }
    
}