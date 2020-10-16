package org.osate.aadl.evaluator.project;

public abstract class Declaration implements Cloneable
{
    public static final String TYPE_FEATURES      = "features";
    public static final String TYPE_PROPERTIES    = "properties";
    public static final String TYPE_SUBCOMPONENTS = "subcomponents";
    public static final String TYPE_CONNECTIONS   = "connections";
    
    private Component parent;
    
    private final String type;
    private String name;
    private String value;

    public Declaration( String type ) 
    {
        this.type = type;
    }

    public Declaration( String type, String name , String value ) 
    {
        this.type = type;
        
        setName( name );
        setValue( value );
    }
    
    public Component getParent()
    {
        return parent;
    }

    public Declaration setParent( Component parent )
    {
        this.parent = parent;
        return this;
    }
    
    public String getType()
    {
        return type;
    }

    /*
    public Declaration setType( String type )
    {
        this.type = type;
        return this;
    }
    */

    public String getName()
    {
        return name;
    }

    public Declaration setName( String name )
    {
        this.name = name;
        return this;
    }

    public String getValue()
    {
        return value;
    }

    public Declaration setValue( String value )
    {
        this.value = value;
        return this;
    }
    
    // ------------------------
    // ------------------------
    // ------------------------
    
    public String getComponentReferenceName()
    {
        if( value == null 
            || value.trim().isEmpty() )
        {
            return null;
        }
        
        int index = value.lastIndexOf( " " );
        return value.substring( index + 1 );
    }
    
    public Component getComponent()
    {
        final String n = getComponentReferenceName();
        
        //  aadlbook::icd::picture
        //  picture
        
        // parent    -> getParent()      -> getParent()
        // Component -> ComponentPackage -> Project
        
        if( n == null || parent == null )
        {
            return null;
        }
        else if( n.contains( ComponentPackage.PACKAGE_SEPARATOR ) )
        {
            return parent.getParent()
                .getParent()
                .getComponent( n );
        }
        else
        {
            return parent.getParent()
                .getComponents()
                .get( n );
        }
    }
    
    public abstract void removeFromParent();
    
    @Override
    public String toString() 
    {
        return getName() + " = " + getValue();
    }
    
    public Declaration clone()
    {
        try
        {
            return (Declaration) super.clone();
        }
        catch( Exception err )
        {
            return null;
        }
    }
    
}