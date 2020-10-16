package org.osate.aadl.evaluator.reqspec;

public class Constant extends NameAndValue
{
    public static final String NAME_SEPARATOR  = ":";
    public static final String VALUE_SEPARATOR = " as ";
    
    private String typeSpec;
    private String propertyName;
    
    public Constant()
    {
        super();
    }

    public Constant( String name , String value )
    {
        super( name , value );
    }

    @Override
    public void setName( String name )
    {
        if( name == null 
            || name.trim().isEmpty() 
            || !name.contains( NAME_SEPARATOR ) )
        {
            super.setName( name );
        }
        else
        {
            String parts[] = name.split( NAME_SEPARATOR );
            
            super.setName( parts[0].trim() );
            setTypeSpec  ( parts[1].trim() );
        }
    }

    @Override
    public void setValue( String value )
    {
        if( value == null 
            || value.trim().isEmpty() 
            || !value.contains( VALUE_SEPARATOR ) )
        {
            super.setName( value );
        }
        else
        {
            String parts[] = value.split( VALUE_SEPARATOR );
            
            super.setValue ( parts[0].trim() );
            setPropertyName( parts[1].trim() );
        }
    }

    public String getTypeSpec()
    {
        return typeSpec;
    }

    public void setTypeSpec( String typeSpec )
    {
        this.typeSpec = typeSpec;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public void setPropertyName( String propertyName )
    {
        this.propertyName = propertyName;
    }
    
}
