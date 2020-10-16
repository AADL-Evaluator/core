package org.osate.aadl.evaluator.file;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osate.aadl.evaluator.file.reqspec.ReqspecProcess;
import org.osate.aadl.evaluator.project.Project;

public class ProjectFile 
{
    private final static Logger LOGGER = Logger.getLogger( ProjectFile.class.getName() );
    
    //private static final String INTEGRATION_FILE = "integration.aadl";
    private static final String AADL_FILE = ".aadl";
    private static final String REQSPEC_FILE = ".reqspec";
    private static final String GOAL_FILE = ".goals";
    private static final String ORGANIZATION_FILE = ".org";
    
    public static final String PACKAGE = "package";
    public static final String EXTENDS = "extends";
    public static final String IMPLEMENTATION = "implementation";
    public static final String GROUP = "group";
    public static final String FEATURES = "features";
    public static final String PROPERTIES = "properties";
    public static final String SUBCOMPONENTS = "subcomponents";
    public static final String CONNECTIONS = "connections";
    public static final String FLOWS = "flows";
    public static final String ANNEX = "annex";
    public static final String COMMENT = "--";
    public static final String ANNEX_END = "**};";
    public static final String COMPONENT_END = "end";
    
    private ProjectFile()
    {
        // faz nada
    }
    
    public static Project open( String directory ) throws Exception
    {
        return open( new File( directory ) );
    }
    
    public static Project open( File directory ) throws Exception
    {
        if( directory == null || !directory.isDirectory() )
        {
            throw new Exception( "Please, select a project." );
        }
        /*
        else if( !new File( directory , INTEGRATION_FILE ).exists() )
        {
            throw new Exception( "the file 'integration.aadl' wasn't found." );
        }
        */
        
        LOGGER.log( Level.INFO , "Open the project {0}" , directory );
        
        Project p = new Project( directory );
        processDirectory( p , directory );
        
        /*
        if( p.getDevices().isEmpty() 
            || p.getComponents().isEmpty() )
        {
            throw new Exception( "No devices was found." );
        }
        */
        
        return p;
    }
    
    private static void processDirectory( Project project , File directory )
    {
        for( File file : directory.listFiles() )
        {
            LOGGER.log( Level.INFO , "processing file {0}" , file.getName() );
            
            if( file.getName().toLowerCase().endsWith( AADL_FILE ) )
            {
                try
                {
                    AadlProcess.process( project , file );
                }
                catch( Exception err )
                {
                    err.printStackTrace();
                }
            }
            else if( file.getName().toLowerCase().endsWith( REQSPEC_FILE ) 
                || file.getName().toLowerCase().endsWith( GOAL_FILE ) 
                || file.getName().toLowerCase().endsWith( ORGANIZATION_FILE ) )
            {
                try
                {
                    ReqspecProcess.process( project.getEspecification() , file );
                }
                catch( Exception err )
                {
                    err.printStackTrace();
                }
            }
            else if( file.isDirectory() 
                && !"scenarios".equalsIgnoreCase( file.getName() ) )
            {
                processDirectory( project , file );
            }
        }
    }
    
}