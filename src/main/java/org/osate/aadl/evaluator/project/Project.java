package org.osate.aadl.evaluator.project;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import org.osate.aadl.evaluator.reqspec.Reqspec;

public class Project
{
    private final File directory;
    private final Map<String,ComponentPackage> packages;
    private final Reqspec especification;
    
    public Project( final File directory )
    {
        this.directory = directory;
        this.packages = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.especification = new Reqspec( this );
    }

    public File getDirectory()
    {
        return directory;
    }
    
    public Map<String,ComponentPackage> getPackages()
    {
        return packages;
    }

    public Reqspec getEspecification()
    {
        return especification;
    }
    
    public Project add( ComponentPackage componentPackage )
    {
        packages.put( componentPackage.getName() , componentPackage );
        return this;
    }
    
    // --------------------------------------------
    // --------------------------------------------
    // --------------------------------------------
    
    public Component getComponent( String name )
    {
        int index = name.lastIndexOf( ComponentPackage.PACKAGE_SEPARATOR );
        
        String packageName = name.substring( 0 , index );
        String componentName = name.substring( index + ComponentPackage.PACKAGE_SEPARATOR.length() );
        
        return getComponent( packageName , componentName );
    }
    
    public Component getComponent( String packageName , String componentName )
    {
        if( !packages.containsKey( packageName ) )
        {
            return null;
        }
        
        Component e = packages.get( packageName )
            .getComponents()
            .get( componentName.toUpperCase() );
        
        if( e == null && componentName.contains( "." ) )  
        {
            componentName = componentName.substring( 
                0 , 
                componentName.lastIndexOf( "." ) 
            );
            
            return packages.get( packageName )
                .getComponents()
                .get( componentName.toUpperCase() );
        }
        else
        {
            return e;
        }
    }
    
}