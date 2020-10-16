package org.osate.aadl.evaluator.file.reqspec;

import java.util.List;
import org.osate.aadl.evaluator.reqspec.DocumentOrSection;

public class DocumentOrSectionProcess extends ElementProcess<DocumentOrSection>
{
    public static final String DOCUMENT = "document";
    public static final String SECTION  = "section";
    
    public DocumentOrSectionProcess( boolean document )
    {
        super( 
            new DocumentOrSection( document ) ,
            DOCUMENT ,
            SECTION ,
            StakeholderGoalProcess.GOAL ,
            SystemRequirementProcess.REQUIREMENT
        );
    }
    
    @Override
    public void setHeader( String line )
    {
        element.setName ( getName( DOCUMENT , line ) );
        element.setTitle( getTitle( line ) );
    }

    @Override
    public int process( int i , List<String> lines , String line )
    {
        if( line.startsWith( StakeholderGoalProcess.GOAL ) )
        {
            final StakeholderGoalProcess p = new StakeholderGoalProcess();
            i = p.process( i , lines );
            
            getElement().getGoals().put( p.getElement().getName() , p.getElement() );
        }
        else if( line.startsWith( SystemRequirementProcess.REQUIREMENT ) )
        {
            final SystemRequirementProcess p = new SystemRequirementProcess();
            i = p.process( i , lines );
            
            getElement().getGoals().put( p.getElement().getName() , p.getElement() );
        }
        
        return i;
    }
    
}
