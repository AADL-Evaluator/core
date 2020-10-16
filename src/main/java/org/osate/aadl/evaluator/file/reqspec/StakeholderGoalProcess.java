package org.osate.aadl.evaluator.file.reqspec;

import java.util.List;
import org.osate.aadl.evaluator.reqspec.StakeholderGoal;

public class StakeholderGoalProcess extends ElementProcess<StakeholderGoal>
{
    public static final String GOAL = "goal";
    
    public static final String CATEGORY = "category";
    public static final String RATIONALE = "rationale";
    public static final String REFINES = "refines";
    public static final String CONFLITCTS_WITH = "conflicts with";
    public static final String EVOLVES = "evolves";
    public static final String DROPPER = "dropper";
    public static final String STAKEHOLDER = "stakeholder";
    public static final String SEE_GOAL = "see goal";
    public static final String SEE_DOCUMENT = "see document";
    public static final String CHANGE_UNCERTAINTY = "ChangeUncertainty";

    public StakeholderGoalProcess()
    {
        super( 
            new StakeholderGoal() ,
            ReqspecProcessUtils.CONSTANT ,
            ReqspecProcessUtils.COMPUTE ,
            ReqspecProcessUtils.WHEN_EXPRESSION ,
            GOAL ,
            CATEGORY ,
            RATIONALE ,
            REFINES ,
            CONFLITCTS_WITH ,
            EVOLVES ,
            DROPPER ,
            STAKEHOLDER ,
            SEE_GOAL ,
            SEE_DOCUMENT ,
            CHANGE_UNCERTAINTY
        );
    }
    
    @Override
    public void setHeader( String line )
    {
        element.setName ( getName( GOAL , line ) );
        element.setTitle( getTitle( line ) );
        element.setTarget( getFor( line , null ) );
    }

    @Override
    public int process( int i , List<String> lines , String line )
    {
        if( line.startsWith( CATEGORY ) )
        {
            getElement().setCategory( getValue( CATEGORY , line ) );
        }
        else if( line.startsWith( RATIONALE ) )
        {
            getElement().setRationale( getValue( RATIONALE , line ) );
        }
        else if( line.startsWith( ReqspecProcessUtils.WHEN_EXPRESSION ) )
        {
            getElement().getWhenConditions().add( line );
        }
        else if( line.startsWith( CONFLITCTS_WITH ) )
        {
            getElement().setConflicts( getValue( CONFLITCTS_WITH , line ) );
        }
        else if( line.startsWith( EVOLVES ) )
        {
            getElement().setEvolves( getValue( EVOLVES , line ) );
        }
        else if( line.startsWith( DROPPER ) )
        {
            getElement().setDropped( getValue( DROPPER , line ) );
        }
        else if( line.startsWith( STAKEHOLDER ) )
        {
            getElement().setStakeholder( getValue( STAKEHOLDER , line ) );
        }
        else if( line.startsWith( SEE_GOAL ) )
        {
            getElement().setSeeGoals( getValue( SEE_GOAL , line ) );
        }
        else if( line.startsWith( SEE_DOCUMENT ) )
        {
            getElement().setSeeDocuments( getValue( SEE_DOCUMENT , line ) );
        }
        else if( line.startsWith( CHANGE_UNCERTAINTY ) )
        {
            getElement().setChangeUncertainty( getValue( CHANGE_UNCERTAINTY , line ) );
        }
        else if( line.startsWith( ReqspecProcessUtils.CONSTANT ) )
        {
            getElement().getConstants().add( 
                ReqspecProcessUtils.getConstant( line ) 
            );
        }
        
        return i;
    }
    
}
