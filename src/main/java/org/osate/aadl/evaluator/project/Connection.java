package org.osate.aadl.evaluator.project;

public class Connection extends Declaration implements Cloneable
{
    public static final String CONNECTOR_BIDIRECT  = "<->";
    public static final String CONNECTOR_UNIDIRECT = "->";
    
    public static final String TYPE_BUS  = "bus access";
    public static final String TYPE_PORT = "port";
    
    private String connectionA;
    private String connectionB;

    public Connection()
    {
        super( "connection" );
    }

    public Connection( String name , String value )
    {
        super( "connection" );
        setName( name );
        setValue( value );
    }

    @Override
    public Connection setValue( String value )
    {
        super.setValue( value );
        
        String connector = value.contains( CONNECTOR_BIDIRECT )
            ? CONNECTOR_BIDIRECT
            : CONNECTOR_UNIDIRECT;
        
        if( !value.contains( connector ) )
        {
            return this;
        }
        
        String parts[] = value.split( connector );
        connectionA = parts[0].trim();
        connectionB = parts[1].trim();
        
        return this;
    }
    
    public String getConnectionA()
    {
        return connectionA;
    }

    public String getConnectionB()
    {
        return connectionB;
    }
    
    public String getSubcomponentAndFeatureA()
    {
        if( connectionA == null )
        {
            return null;
        }
        
        return connectionA.substring( 
            connectionA.lastIndexOf( " " ) + 1 
        );
    }
    
    public String getSubcomponentAndFeatureB()
    {
        if( connectionB == null )
        {
            return null;
        }
        
        return connectionB.substring( 
            connectionB.lastIndexOf( " " ) + 1 
        );
    }
    
    public String getSubcomponentNameA()
    {
        if( connectionA == null )
        {
            return null;
        }
        
        int last = connectionA.contains( "." )
            ? connectionA.lastIndexOf( "." )
            : connectionA.length();
        
        return connectionA.substring( 
            connectionA.lastIndexOf( " " ) + 1 ,
            last
        );
    }

    public String getSubcomponentNameB()
    {
        if( connectionB == null )
        {
            return null;
        }
        
        int last = connectionB.contains( "." )
            ? connectionB.lastIndexOf( "." )
            : connectionB.length();
        
        return connectionB.substring( 
            connectionB.lastIndexOf( " " ) + 1 ,
            last
        );
    }
    
    public String getFeatureNameA()
    {
        if( connectionA == null
            || !connectionA.contains( "." ) )
        {
            return null;
        }
        
        return connectionA.substring( 
            connectionA.lastIndexOf( "." ) + 1
        );
    }

    public String getFeatureNameB()
    {
        if( connectionB == null 
            || !connectionB.contains( "." ) )
        {
            return null;
        }
        
        return connectionB.substring( 
            connectionB.lastIndexOf( "." ) + 1 
        );
    }
    
    public Subcomponent getSubcomponentA()
    {
        String name = getSubcomponentNameA();
        if( name == null 
            || name.trim().isEmpty() 
            || getParent() == null )
        {
            return null;
        }
        
        return getParent().getSubcomponent( name );
    }
    
    public Subcomponent getSubcomponentB()
    {
        String name = getSubcomponentNameB();
        if( name == null 
            || name.trim().isEmpty() 
            || getParent() == null )
        {
            return null;
        }
        
        return getParent().getSubcomponent( name );
    }
    
    public Component getComponentA()
    {
        Subcomponent comp = getSubcomponentA();
        if( comp == null )
        {
            return null;
        }
        
        return comp.getComponent();
    }
    
    public Component getComponentB()
    {
        Subcomponent comp = getSubcomponentB();
        if( comp == null )
        {
            return null;
        }
        
        return comp.getComponent();
    }
    
    public Feature getFeatureA()
    {
        String name = getFeatureNameA();
        if( name == null || name.trim().isEmpty() )
        {
            return null;
        }
        
        Component comp = getComponentA();
        if( comp == null )
        {
            return null;
        }
        
        return comp.getFeature( name );
    }
    
    public Feature getFeatureB()
    {
        String name = getFeatureNameB();
        if( name == null || name.trim().isEmpty() )
        {
            return null;
        }
        
        Component comp = getComponentB();
        if( comp == null )
        {
            return null;
        }
        
        return comp.getFeature( name );
    }
    
    public boolean isBidirect()
    {
        return getValue().contains( CONNECTOR_BIDIRECT );
    }
    
    @Override
    public String getComponentReferenceName()
    {
        return null;
    }

    @Override
    public void removeFromParent() 
    {
        if( getParent() == null )
        {
            return ;
        }
        
        getParent().getConnections().remove( getName() );
    }
    
    @Override
    public Connection clone()
    {
        return new Connection( getName() , getValue() );
    }
    
}