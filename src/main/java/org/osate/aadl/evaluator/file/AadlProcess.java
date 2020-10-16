package org.osate.aadl.evaluator.file;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import static org.osate.aadl.evaluator.file.ProjectFile.ANNEX;
import static org.osate.aadl.evaluator.file.ProjectFile.ANNEX_END;
import static org.osate.aadl.evaluator.file.ProjectFile.COMMENT;
import static org.osate.aadl.evaluator.file.ProjectFile.GROUP;
import static org.osate.aadl.evaluator.file.ProjectFile.IMPLEMENTATION;
import static org.osate.aadl.evaluator.file.ProjectFile.PACKAGE;
import static org.osate.aadl.evaluator.file.ProjectFileUtils.isComponentEnd;
import static org.osate.aadl.evaluator.file.ProjectFileUtils.isComponentStart;
import static org.osate.aadl.evaluator.file.ProjectFileUtils.isDeclarationStart;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.ComponentPackage;
import org.osate.aadl.evaluator.project.Declaration;
import org.osate.aadl.evaluator.project.Project;

public class AadlProcess 
{
    
    public static void process( Project project , File file ) throws Exception
    {
        ComponentPackage componentPackage = new ComponentPackage();
        componentPackage.setParent( project );
        componentPackage.setFile( file );
        
        Component component = new Component();
        component.setParent( componentPackage );
        component.setName( "" );
        
        List<String> content = componentPackage.getStartLines();
        String declarationType = null;
        
        List<String> lines = Files.readAllLines( file.toPath() );
        
        for( int i = 0 ; i < lines.size() ; i++ )
        {
            String line  = ProjectFileUtils.removeComment( lines.get( i ) );
            String lower = line.trim().toLowerCase();
            
            if( lower == null
                || lower.isEmpty()
                || lower.startsWith( COMMENT ) )
            {
                //faz nada
            }
            else if( lower.startsWith( PACKAGE ) )
            {
                componentPackage.setName( line.trim().split( " " )[ 1 ] );
            }
            else if( lower.startsWith( ANNEX ) 
                || lower.startsWith( ANNEX_END ) 
                || ANNEX.equals( declarationType ) )
            {
                declarationType = lower.startsWith( ANNEX_END ) 
                    ? null 
                    : ANNEX;
            }
            else if( isDeclarationStart( lower ) )
            {
                declarationType = lower;
            }
            else if( isComponentEnd( lower , component ) )
            {
                component.getContent().add( line );
                componentPackage.add( component );
                
                component = new Component();
                component.setName( "" );
                component.setParent( componentPackage );
                
                content = component.getContent();
                
                declarationType = null;
            }
            else if( declarationType != null )
            {
                DeclarationProcess dp = new DeclarationProcess( declarationType , lines , i );
                Declaration d = dp.getDeclaration( lower , line );
                i = dp.getI();
                
                component.add( d );
            }
            else if( isComponentStart( lower ) )
            {                
                setComponent( component , line );
                content = component.getContent();
            }
            
            content.add( line );
        }
        
        componentPackage.getEndLines().addAll( component.getContent() );
        project.add( componentPackage );
    }
    
    private static void setComponent( Component component , String line )
    {
        String parts[] = ProjectFileUtils.removeDoubleSpace( line )
            .trim()
            .split( " " );

        boolean virtual = Component.TYPE_VIRTUAL.equalsIgnoreCase( parts[0] );
        boolean implementation = IMPLEMENTATION.equalsIgnoreCase( parts[1] );
        boolean group = GROUP.equalsIgnoreCase( parts[1] );
        boolean isNameSecond = implementation || group || virtual;

        component.setType( parts[0] );
        component.setName( parts[ isNameSecond ? 2 : 1 ] );
        component.setImplementation( implementation );
        component.setGroup( group );
        component.setVirtualType( virtual ? parts[1] : null );

        if( (isNameSecond && parts.length > 3) 
            || (!isNameSecond && parts.length > 2) )
        {
            component.setExtend( parts[ parts.length - 1 ] );
        }
    }
    
}
