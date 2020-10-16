package org.osate.aadl.evaluator.file.reqspec;

import java.util.Arrays;
import org.osate.aadl.evaluator.reqspec.Compute;
import org.osate.aadl.evaluator.reqspec.Constant;

public class ReqspecProcessUtils 
{
    public static final String COMMENT = "//";
    
    public static final String CONSTANT = "val";
    public static final String CONSTANT_SEPARATOR = "=";
    
    public static final String COMPUTE  = "compute";
    public static final String COMPUTE_SEPARATOR  = ":";
    
    public static final String WHEN_IN_MODES = "when in modes";
    public static final String WHEN_IN_ERROR_STATE = "when in error state";
    public static final String WHEN_EXPRESSION = "when";
    
    private ReqspecProcessUtils()
    {
        // do nothing
    }
    
    public static boolean isComment( String line )
    {
        return line.startsWith( COMMENT );
    }
    
    public static String removeComment( String line )
    {
        int index = line.indexOf( COMMENT );
        
        switch ( index ) 
        {
            case -1: return line;
            case 0 : return "";
            default: return line.substring( 0 , index );
        }
    }
    
    public static String getValue( String field , String value )
    {
        return value.substring( field.length() + 1 );
    }
    
    public static Constant getConstant( String line )
    {
        String l = getValue( CONSTANT , line );
        int index = l.indexOf( CONSTANT_SEPARATOR );
        
        return new Constant( 
            l.substring( 0 , index ).trim() , 
            l.substring( index + 1 ).trim()
        );
    }
    
    public static Compute getCompute( String line )
    {
        String l = getValue( COMPUTE , line );
        int index = l.indexOf( COMPUTE_SEPARATOR );
        
        return new Compute( 
            l.substring( 0 , index ).trim() , 
            l.substring( index + 1 ).trim()
        );
    }
    
    // from: https://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java
    public static <T> T[] concat( T[] first , T[] second )
    {
        T[] result = Arrays.copyOf( first , first.length + second.length );
        System.arraycopy( second , 0 , result , first.length , second.length );
        
        return result;
    }
    
}