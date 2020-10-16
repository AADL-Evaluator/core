package org.osate.aadl.evaluator.file.reqspec;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.osate.aadl.evaluator.reqspec.Reqspec;

public class ReqspecProcess 
{
    public static final String BLOCK_START = "[";
    public static final String BLOCK_END   = "]";
    
    private ReqspecProcess()
    {
        // faz anda
    }
    
    public static Reqspec process( Reqspec reqspec , File file ) throws IOException
    {
        List<String> lines = Files.readAllLines( file.toPath() );
        
        System.out.println( new String( 
            Files.readAllBytes( file.toPath() ) 
        ) );
        
        for( int i = 0 ; i < lines.size() ; i++ )
        {
            String line = lines.get( i ).trim();
            
            if( line.startsWith( OrganizationProcess.ORGANIZATION ) )
            {
                final OrganizationProcess p = new OrganizationProcess();
                i = p.process( i , lines );
                
                reqspec.add( p.getOrganization() );
            }
            else if( line.startsWith( SystemRequirementsProcess.SYSTEM_REQUIREMENTS ) )
            {
                final SystemRequirementsProcess p = new SystemRequirementsProcess( false );
                i = p.process( i , lines );
                
                reqspec.add( p.getElement() );
            }
            else if( line.startsWith( SystemRequirementsProcess.GLOBAL_REQUIREMENTS ) )
            {
                final SystemRequirementsProcess p = new SystemRequirementsProcess( true );
                i = p.process( i , lines );
                
                reqspec.add( p.getElement() );
            }
            else if( line.startsWith( DocumentOrSectionProcess.DOCUMENT ) )
            {
                final DocumentOrSectionProcess p = new DocumentOrSectionProcess( false );
                i = p.process( i , lines );
                
                reqspec.add( p.getElement() );
            }
            else if( line.startsWith( DocumentOrSectionProcess.SECTION ) )
            {
                final DocumentOrSectionProcess p = new DocumentOrSectionProcess( true );
                i = p.process( i , lines );
                
                reqspec.add( p.getElement() );
            }
        }
        
        return reqspec;
    }
    
}