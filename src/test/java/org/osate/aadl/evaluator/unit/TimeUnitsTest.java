package org.osate.aadl.evaluator.unit;

import org.junit.Assert;
import org.junit.Test;

public class TimeUnitsTest 
{
    
    @Test
    public void comparar()
    {
        Assert.assertEquals( "1.0E-5 s" , TimeUtils.convert( "10us" , "s" ) );
        
        Assert.assertEquals( "1.0 s" , TimeUtils.convert( "1000000 us" , "s" ) );
        Assert.assertEquals( "1.0 s" , TimeUtils.convert( "1000 ms"    , "s" ) );
        Assert.assertEquals(   "1 s" , TimeUtils.convert( "1 s" , "s" ) );
        
        Assert.assertEquals( "60.0 s" , TimeUtils.convert( "1 m" , "s" ) );
        Assert.assertEquals( "24.0 h" , TimeUtils.convert( "1 d" , "h" ) );
        
        // ---- //
        
        Assert.assertEquals( "getValue" , 1.0E-5 , TimeUtils.getValue( "10us" , "s" ) , 0 );
    }
    
}