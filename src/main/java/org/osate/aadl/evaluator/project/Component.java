package org.osate.aadl.evaluator.project;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.osate.aadl.evaluator.reqspec.SystemRequirements;

public class Component implements Cloneable
{
    public static final String TYPE_DATA = "data";
    public static final String TYPE_DEVICE = "device";
    public static final String TYPE_BUS = "bus";
    public static final String TYPE_PROCESS = "process";
    public static final String TYPE_PROCESSOR = "processor";
    public static final String TYPE_SYSTEM = "system";
    public static final String TYPE_MEMORY = "memory";
    public static final String TYPE_THREAD = "thread";
    public static final String TYPE_ABSTRACT = "abstract";
    public static final String TYPE_SUBPROGRAM = "subprogram";
    public static final String TYPE_FEATURE = "feature";
    public static final String TYPE_VIRTUAL = "virtual";
    
    // bus implementation
    // system implementation
    
    private ComponentPackage parent;
    
    private String type;
    private String name;
    private String extend;
    private boolean implementation;
    private boolean group;
    private String virtualType;
    private final Map<String,Feature> features;
    private final Map<String,Flow> flows;
    private final Map<String,Subcomponent> subcomponents;
    private final Map<String,Connection> connections;
    private final List<Property> properties;
    private final List<String> content;
    
    public Component()
    {
        this.subcomponents = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.connections = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.properties = new LinkedList<>();
        this.features = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.flows = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.content = new LinkedList<>();
    }

    public ComponentPackage getParent()
    {
        return parent;
    }

    public Component setParent( ComponentPackage parent )
    {
        this.parent = parent;
        return this;
    }

    public String getType()
    {
        return type;
    }
    
    public void setType( String type )
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getExtend()
    {
        return extend;
    }

    public void setExtend( String extend )
    {
        this.extend = extend;
    }

    public boolean isImplementation()
    {
        return implementation;
    }

    public void setImplementation( boolean implementation )
    {
        this.implementation = implementation;
    }

    public boolean isGroup()
    {
        return group;
    }

    public void setGroup( boolean group )
    {
        this.group = group;
    }

    public String getVirtualType()
    {
        return virtualType;
    }

    public void setVirtualType( String virtualType )
    {
        this.virtualType = virtualType;
    }

    public Map<String,Feature> getFeatures()
    {
        return features;
    }
    
    public List<Property> getProperties()
    {
        return properties;
    }
    
    public Map<String, Subcomponent> getSubcomponents()
    {
        return subcomponents;
    }

    public Map<String,Connection> getConnections()
    {
        return connections;
    }

    public Map<String, Flow> getFlows() 
    {
        return flows;
    }
    
    public List<String> getContent()
    {
        return content;
    }
    
    // --------------------------------
    // -------------------------------- HELPERS
    // --------------------------------
    
    /**
     * join package name + :: + component name.
     * 
     * @return package name + :: + component name
     */
    public String getFullName()
    {
        return getParent().getName() 
            + ComponentPackage.PACKAGE_SEPARATOR 
            + getName();
    }
    
    public Component add( Declaration declaration )
    {
        if( declaration == null )
        {
            return this;
        }
        
        declaration.setParent( this );
        
        if( declaration instanceof Connection )
        {
            connections.put( declaration.getName() , (Connection) declaration );
        }
        else if( declaration instanceof Feature )
        {
            features.put( declaration.getName() , (Feature) declaration );
        }
        else if( declaration instanceof Subcomponent )
        {
            subcomponents.put( declaration.getName() , (Subcomponent) declaration );
        }
        else if( declaration instanceof Property )
        {
            properties.add( (Property) declaration );
        }
        else if( declaration instanceof Flow )
        {
            flows.put( declaration.getName() , (Flow) declaration );
        }
        
        return this;
    }
    
    public String getComponentNamedImplemented()
    {
        if( !isImplementation() 
            || getName() == null 
            || getName().trim().isEmpty() 
            || !getName().contains( "." ) )
        {
            return null;
        }
        
        return getName().substring( 
            0 , 
            getName().indexOf( "." ) 
        );
    }
    
    public Component getComponentImplemented()
    {
        String name = getComponentNamedImplemented();
        if( name == null )
        {
            return null;
        }
        
        return parent.getComponent( name );
    }
    
    public Component getComponentExtended()
    {
        if( getExtend() == null 
            || getExtend().trim().isEmpty() )
        {
            return null;
        }
        
        return parent.getComponent( getExtend() );
    }
    
    // ----------------
    // ---------------- GET ALL ( THIS , IMPLEMENTED AND EXTENDED )
    // ----------------
    
    public Map<String,Connection> getConnectionsAll()
    {
        Component implemented = getComponentImplemented();
        Component extended = getComponentExtended();
        
        Map<String,Connection> all = new LinkedHashMap<>();
        
        if( implemented != null )
        {
            all.putAll( implemented.getConnectionsAll() );
        }
        
        if( extended != null )
        {
            all.putAll( extended.getConnectionsAll() );
        }
        
        all.putAll( getConnections() );
        
        return all;
    }
    
    public Map<String,Subcomponent> getSubcomponentsAll()
    {
        Component implemented = getComponentImplemented();
        Component extended = getComponentExtended();
        
        Map<String,Subcomponent> all = new LinkedHashMap<>();
        
        if( implemented != null )
        {
            all.putAll( implemented.getSubcomponentsAll() );
        }
        
        if( extended != null )
        {
            all.putAll( extended.getSubcomponentsAll() );
        }
        
        all.putAll( getSubcomponents() );
        
        return all;
    }
    
    public Map<String,Feature> getFeaturesAll()
    {
        Component implemented = getComponentImplemented();
        Component extended = getComponentExtended();
        
        Map<String,Feature> all = new LinkedHashMap<>();
        
        if( implemented != null )
        {
            all.putAll( implemented.getFeaturesAll() );
        }
        
        if( extended != null )
        {
            all.putAll( extended.getFeaturesAll() );
        }
        
        all.putAll( getFeatures() );
        
        return all;
    }
    
    public List<Property> getPropertiesAll()
    {
        Component implemented = getComponentImplemented();
        Component extended = getComponentExtended();
        
        List<Property> all = new LinkedList<>();
        
        if( implemented != null )
        {
            all.addAll(implemented.getPropertiesAll() );
        }
        
        if( extended != null )
        {
            all.addAll(extended.getPropertiesAll() );
        }
        
        all.addAll( getProperties() );
        
        return all;
    }
    
    // ----------------
    // ---------------- GET BY NAME
    // ----------------
    
    public Connection getConnection( String name )
    {
        return getConnectionsAll().get( name );
    }
    
    public Subcomponent getSubcomponent( String name )
    {
        return getSubcomponentsAll().get( name );
    }
    
    public Feature getFeature( String name )
    {
        return getFeaturesAll().get( name );
    }
    
    public List<Property> getProperty( final String... names )
    {
        final List<Property> result = new LinkedList<>();
        
        for( Property p : getPropertiesAll() )
        {
            for( String name : names )
            {
                if( p.getName().equalsIgnoreCase( name ) )
                {
                    result.add( p );
                    break ;
                }
            }
        }
        
        return result;
    }
    
    public List<SystemRequirements> getRequirements()
    {
        return getParent()
            .getParent()
            .getEspecification()
            .getRequirementByComponent( getFullName() );
    }
    
    @Override
    public String toString() 
    {
        return getName();
    }

    @Override
    public Component clone() 
    {
        Component c = new Component();
        c.setExtend( extend );
        c.setImplementation( implementation );
        c.setName( name );
        c.setParent( parent );
        c.setType( type );
        
        for( Feature d : getFeatures().values() )
        {
            c.add( d.clone() );
        }
        
        for( Subcomponent d : getSubcomponents().values() )
        {
            c.add( d.clone() );
        }
        
        for( Connection d : getConnections().values() )
        {
            c.add( d.clone() );
        }
        
        for( Property d : getProperties() )
        {
            c.add( d.clone() );
        }
        
        return c;
    }
    
}