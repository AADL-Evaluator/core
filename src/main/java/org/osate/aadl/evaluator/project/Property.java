package org.osate.aadl.evaluator.project;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Property extends Declaration implements Cloneable
{
    /*
        Bus_Properties::Available_Bandwidth => ( 100000 bitsps , 400000 bitsps, 118000 bitsps);
        Latency => 1 Ms .. 1 Ms;
	SEI::BandWidthCapacity => 500000.0 bitsps; 
	Transmission_Time => [ Fixed => 10 ms .. 30ms;
                          PerByte => 1 us .. 10 us; ];
    */
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_VALUE   = 0;
    public static final int TYPE_MIN_MAX = 1;
    public static final int TYPE_OBJECT  = 2;
    public static final int TYPE_ARRAY   = 3;
    public static final int TYPE_STRING  = 4;
    
    public static final String STRING_START = "\"";
    public static final String STRING_END   = "\"";
    
    public static final String OBJECT_START = "[";
    public static final String OBJECT_END   = "]";
    
    public static final String ARRAY_START = "(";
    public static final String ARRAY_END   = ")";
    
    public static final String SEPARATOR = "=>";
    public static final String MIN_MAX_SEPARATOR = "..";
    public static final String ARRAY_SEPARATOR = ",";
    public static final String OBJECT_SEPARATOR = ";";
    
    public static final String MIN = "min";
    public static final String MAX = "max";
    
    public Property() 
    {
        super( "property" );
    }
    
    public Property( String name , String value ) 
    {
        super( "property" );
        setName( name );
        setValue( value );
    }

    public int getValueType()
    {
        if( getValue() == null || getValue().trim().isEmpty() )
        {
            return TYPE_UNKNOWN;
        }
        else if( getValue().startsWith( OBJECT_START ) 
                && getValue().endsWith( OBJECT_END ) )
        {
            return TYPE_OBJECT;
        }
        else if( getValue().startsWith( ARRAY_START ) 
                && getValue().endsWith( ARRAY_END ) )
        {
            return TYPE_ARRAY;
        }
        else if( getValue().startsWith( STRING_START ) 
                && getValue().endsWith( STRING_END ) )
        {
            return TYPE_ARRAY;
        }
        else if( getValue().contains( MIN_MAX_SEPARATOR ) )
        {
            return TYPE_MIN_MAX;
        }
        else
        {
            return TYPE_VALUE;
        }
    }

    @Override
    public String getValue() 
    {
        String value = super.getValue();
        
        if( value == null 
            || value.trim().isEmpty() )
        {
            return value;
        }
        
        return value.startsWith( STRING_START ) && value.endsWith( STRING_END )
            ? value.substring( 1 , value.length() - 1 )
            : value;
    }
    
    public Map<String,String> getValueMinAndMax()
    {
        if( getValue() == null 
            || getValue().trim().isEmpty() 
            || !getValue().contains( MIN_MAX_SEPARATOR ) )
        {
            return null;
        }
        
        final String parts[] = getValue().split( 
            Pattern.quote( MIN_MAX_SEPARATOR ) 
        );
        
        Map<String,String> values = new HashMap<>();
        values.put( MIN , parts[0].trim() );
        values.put( MAX , parts[1].trim() );
        
        return values;
    }
    
    public Map<String,Property> getValueObject()
    {
        if( getValue() == null 
            || getValue().trim().isEmpty() 
            || !getValue().trim().startsWith( OBJECT_START ) )
        {
            return null;
        }
        
        String v = getValue().substring( 1 , getValue().length() - 1 );
        
        Map<String,Property> values = new HashMap<>();
        for( String part : v.split( OBJECT_SEPARATOR ) )
        {
            String p[] = part.split( SEPARATOR );
            
            values.put( 
                p[0].trim() , 
                (Property) new Property()
                    .setName( p[0].trim() ) 
                    .setValue( p[1].trim() )
            );
        }
        
        
        return values;
    }
    
    public List<String> getValueArray()
    {
        if( getValue() == null 
            || getValue().trim().isEmpty() 
            || !getValue().trim().startsWith( ARRAY_START ) )
        {
            return null;
        }
        
        String v = getValue().substring( 1 , getValue().length() - 1 );
        
        List<String> values = new LinkedList<>();
        for( String value : v.split( ARRAY_SEPARATOR ) )
        {
            values.add( value.trim() );
        }
        
        return values;
    }

    @Override
    public void removeFromParent() 
    {
        if( getParent() == null )
        {
            return ;
        }
        
        getParent().getProperties().remove( this );
    }
    
    @Override
    public Property clone()
    {
        return new Property( getName() , getValue() );
    }
    
}