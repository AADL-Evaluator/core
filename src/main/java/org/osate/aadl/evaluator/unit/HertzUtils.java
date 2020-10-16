package org.osate.aadl.evaluator.unit;

public class HertzUtils extends UnitUtils
{
    private static final String[] UNITS_HERTZ = { 
        "yHz" , 
        "zHz" , 
        "aHz" , 
        "fHz" , 
        "pHz" , 
        "nHz" , 
        "µHz" , 
        "mHz" , 
        "cHz" , 
        "dHz" , 
        
        "Hz" , 
        
        "daHz" ,
        "hHz" ,
        "kHz" ,
        "MHz" ,
        "GHz" ,
        "THz" ,
        "PHz" ,
        "EHz" ,
        "ZHz" ,
        "YHz"
    };
    
    private static final int[] UNITS_FACTORS = { 
        -24 , 
        -21 , 
        -18 , 
        -15 , 
        -12 , 
        -9 , 
        -6 , 
        -3 , 
        -2 , 
        -1 ,
        
        0 ,
        
        1 ,
        2 ,
        3 ,
        6 ,
        9 ,
        12 ,
        15 ,
        18 ,
        21 ,
        24
    };
    
    private HertzUtils()
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
        
        String valueStr = getValueAndUnit( 
            convert( value , unit , UNITS_HERTZ , 10 , UNITS_FACTORS ) 
        )[0];
        
        if( isEmpty( valueStr ) )
        {
            return 0;
        }
        
        return Double.parseDouble( valueStr );
    }
    
}