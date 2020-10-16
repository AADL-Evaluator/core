package org.osate.aadl.evaluator.file;

import java.text.MessageFormat;
import java.util.regex.Pattern;
import static org.osate.aadl.evaluator.file.ProjectFile.ANNEX;
import static org.osate.aadl.evaluator.file.ProjectFile.COMPONENT_END;
import static org.osate.aadl.evaluator.file.ProjectFile.CONNECTIONS;
import static org.osate.aadl.evaluator.file.ProjectFile.FEATURES;
import static org.osate.aadl.evaluator.file.ProjectFile.FLOWS;
import static org.osate.aadl.evaluator.file.ProjectFile.PROPERTIES;
import static org.osate.aadl.evaluator.file.ProjectFile.SUBCOMPONENTS;
import org.osate.aadl.evaluator.project.Component;

public class ProjectFileUtils 
{
    private static final String COMPONENT_END_REGEX = "end(\\s)*{0}";
    
    private ProjectFileUtils()
    {
        // do nothing
    }
    
    public static boolean isDeclarationStart( String lower )
    {
        return SUBCOMPONENTS.equals( lower )
            || FEATURES.equals( lower )
            || CONNECTIONS.equals( lower )
            || PROPERTIES.equals( lower )
            || FLOWS.equals( lower )
            || ANNEX.equals( lower )
        ;
    }
    
    public static boolean isComponentStart( String lower )
    {
        return lower.startsWith( Component.TYPE_BUS )
            || lower.startsWith(Component.TYPE_PROCESS ) 
            || lower.startsWith(Component.TYPE_PROCESSOR ) 
            || lower.startsWith( Component.TYPE_DATA ) 
            || lower.startsWith( Component.TYPE_ABSTRACT ) 
            || lower.startsWith( Component.TYPE_DEVICE ) 
            || lower.startsWith( Component.TYPE_MEMORY ) 
            || lower.startsWith( Component.TYPE_THREAD ) 
            || lower.startsWith( Component.TYPE_FEATURE ) 
            || lower.startsWith( Component.TYPE_SYSTEM ) 
            || lower.startsWith( Component.TYPE_VIRTUAL ) 
            || lower.startsWith( Component.TYPE_SUBPROGRAM );
    }
    
    public static boolean isComponentEnd( String lower , Component component )
    {
        if( component == null
            || component.getName() == null 
            || component.getName().isEmpty() 
            || !lower.startsWith( COMPONENT_END ) )
        {
            return false;
        }
        
        Pattern pattern = Pattern.compile( MessageFormat.format( 
            COMPONENT_END_REGEX , component.getName()
        ) , Pattern.CASE_INSENSITIVE );
        
        return pattern.matcher( lower ).find();
    }
    
    public static String removeComment( String line )
    {
        int index = line.indexOf( "--" );
        
        return index == -1 
            ? line 
            : line.substring( 0 , index );
    }
    
    public static String removeAspas( String line )
    {
        if( !line.startsWith( "\"" ) 
            || !line.endsWith( "\"" ) )
        {
            return line;
        }
        
        return line.substring( 1 , line.length() - 1 ).trim();
    }
    
    public static String removeDoubleSpace( String line )
    {
        return line.replaceAll( 
            Pattern.quote( "  " ) , 
            " " 
        );
    }
    
}