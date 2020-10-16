package org.osate.aadl.evaluator.file.reqspec;

import java.util.List;
import org.osate.aadl.evaluator.reqspec.StakeholderGoals;

public class StakeholderGoalsProcess extends ElementProcess<StakeholderGoals>
{
    public static final String GOAL = "goal";
    
    public static final String USE_CONSTANTS = "use constants";
    public static final String SEE_DOCUMENT  = "see document";

    public StakeholderGoalsProcess()
    {
        super( 
            new StakeholderGoals() ,
            GOAL ,
            USE_CONSTANTS ,
            SEE_DOCUMENT
        );
    }
    
    @Override
    public void setHeader( String line )
    {
        element.setName ( getName( GOAL , line ) );
        element.setTitle( getTitle( line ) );
        element.setTarget( getFor( line , null ) );
        element.setUseConstants( getName( USE_CONSTANTS , line ) );
    }

    @Override
    public int process( int i , List<String> lines , String line )
    {
        if( line.startsWith( SEE_DOCUMENT ) )
        {
            getElement().setSeeDocument( getValue( SEE_DOCUMENT , line ) );
        }
        else if( line.startsWith( StakeholderGoalProcess.GOAL ) )
        {
            final StakeholderGoalProcess p = new StakeholderGoalProcess();
            i = p.process( i , lines );
            
            getElement().add( p.getElement() );
        }
        else if( line.startsWith( ReqspecProcessUtils.CONSTANT ) )
        {
            getElement().add( 
                ReqspecProcessUtils.getConstant( line ) 
            );
        }
        
        return i;
    }
    
}
