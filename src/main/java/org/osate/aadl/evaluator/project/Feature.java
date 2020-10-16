package org.osate.aadl.evaluator.project;

public class Feature extends Declaration implements Cloneable
{
    public static final String EVENT_OUT = "out event";
    public static final String EVENT_IN  = "in event";
    
    public static final String PORT_OUT = "out data port";
    public static final String PORT_IN  = "in data port";
    
    public static final String BUS  = "requires bus access";

    public Feature()
    {
        super( "feature" );
    }

    public Feature( String name , String value )
    {
        super( "feature" );
        setName( name );
        setValue( value );
    }
    
    public String getFeatureType()
    {
        return getValue().substring(
            0 , 
            getValue().lastIndexOf( " " ) 
        ).trim();
    }
    
    public boolean isPortOut()
    {
        return getValue().startsWith( PORT_OUT );
    }
    
    public boolean isPortIn()
    {
        return getValue().startsWith( PORT_IN );
    }
    
    public boolean isEventOut()
    {
        return getValue().startsWith( EVENT_OUT );
    }
    
    public boolean isEventIn()
    {
        return getValue().startsWith( EVENT_IN );
    }
    
    public boolean isBus()
    {
        return getValue().startsWith( BUS );
    }
    
    @Override
    public void removeFromParent() 
    {
        if( getParent() == null )
        {
            return ;
        }
        
        getParent().getFeatures().remove( getName() );
    }

    public boolean isValueEquals( Feature other )
    {
        //boolean isType = getFeatureType().equalsIgnoreCase( other.getFeatureType() );
        return getValue().equalsIgnoreCase( other.getValue() );
    }
    
    @Override
    public Feature clone()
    {
        return new Feature( getName() , getValue() );
    }
    
}
