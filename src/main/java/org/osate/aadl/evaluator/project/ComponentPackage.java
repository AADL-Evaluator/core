package org.osate.aadl.evaluator.project;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ComponentPackage implements Cloneable
{
    public static final String PACKAGE_SEPARATOR = "::";
    
    private Project parent;
    
    private String name;
    private File file;
    private final Map<String,Component> components;
    private final List<String> startLines;
    private final List<String> endLines;

    public ComponentPackage() 
    {
        this( "" );
    }
    
    public ComponentPackage( String name ) 
    {
        this.name = name;
        this.components  = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.startLines = new LinkedList<>();
        this.endLines   = new LinkedList<>();
    }

    public Project getParent()
    {
        return parent;
    }

    public ComponentPackage setParent( Project parent )
    {
        this.parent = parent;
        return this;
    }
    
    public String getName()
    {
        return name;
    }

    public ComponentPackage setName( String name )
    {
        this.name = name;
        return this;
    }

    public File getFile()
    {
        return file;
    }

    public ComponentPackage setFile( File file )
    {
        this.file = file;
        return this;
    }
    
    public Map<String,Component> getComponents()
    {
        return components;
    }

    public List<String> getStartLines()
    {
        return startLines;
    }

    public List<String> getEndLines()
    {
        return endLines;
    }
    
    public ComponentPackage add( Component component )
    {
        component.setParent( this );
        components.put( component.getName() , component );
        
        return this;
    }

    // ------------------------
    // ------------------------
    // ------------------------
    
    public boolean isThisPackage( String packageName )
    {
        if( packageName == null 
            || packageName.trim().isEmpty() 
            || name == null 
            || name.trim().isEmpty() 
            || packageName.length() != name.length() )
        {
            return false;
        }
        
        return name.equalsIgnoreCase( packageName );
    }
    
    public Component getComponent( final String full )
    {
        String name = full;
        
        if( full.contains( ComponentPackage.PACKAGE_SEPARATOR ) )
        {
            int index = full.lastIndexOf( PACKAGE_SEPARATOR );
            
            String packageName = full.substring( 0 , index ).trim();
            name = full.substring( index + PACKAGE_SEPARATOR.length() );
            
            if( !isThisPackage( packageName ) )
            {
                return parent.getComponent( packageName , name );
            }
        }
        
        return getComponents().get( name.toUpperCase() );
    }
    
    @Override
    public String toString() 
    {
        return getName();
    }

    @Override
    public ComponentPackage clone()
    {
        ComponentPackage pack = new ComponentPackage();
        pack.setFile( file );
        pack.setName( name );
        pack.setParent( parent );
        
        for( Component component : components.values() )
        {
            pack.add( component.clone() );
        }
        
        return pack;
    }
    
}
