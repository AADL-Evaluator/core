package org.osate.aadl.evaluator.file.reqspec;

import java.util.List;
import org.osate.aadl.evaluator.reqspec.SystemRequirement;

public class SystemRequirementProcess extends ElementProcess<SystemRequirement>
{
    public static final String REQUIREMENT = "requirement";
    
    public static final String CATEGORY = "category";
    public static final String RATIONALE = "rationale";
    public static final String MITIGATES = "mitigates";
    public static final String REFINES = "refines";
    public static final String EVOLVES = "evolves";
    public static final String DECOMPOSES = "decomposes";
    public static final String INHERITS = "inherits";
    public static final String DROPPER = "dropper";
    public static final String DEVELOPMENT_STAKEHOLDER = "development stakeholder";
    public static final String SEE_GOAL = "see goal";
    public static final String SEE_DOCUMENT = "see document";
    public static final String SEE_REQUIREMENT = "see requirement";
    public static final String CHANGE_UNCERTAINTY = "ChangeUncertainty";
    public static final String VALUE_PREDICATE = "value predicate";
    
    public SystemRequirementProcess()
    {
        super( 
            new SystemRequirement() ,
            ReqspecProcessUtils.CONSTANT ,
            ReqspecProcessUtils.COMPUTE ,
            ReqspecProcessUtils.WHEN_EXPRESSION ,
            CATEGORY ,
            RATIONALE ,
            MITIGATES ,
            REFINES ,
            EVOLVES ,
            DECOMPOSES ,
            INHERITS ,
            DROPPER ,
            DEVELOPMENT_STAKEHOLDER ,
            SEE_GOAL ,
            SEE_DOCUMENT ,
            SEE_REQUIREMENT ,
            CHANGE_UNCERTAINTY ,
            VALUE_PREDICATE
        );
    }

    @Override
    public int process( int start , List<String> lines )
    {
        System.out.println( "   [REQUIREMENT][PROCESS] start" );
        int i = super.process( start , lines );
        System.out.println( "   [REQUIREMENT][PROCESS] end!" );
        
        return i;
    }
    
    @Override
    public void setHeader( String line )
    {
        element.setName ( getName( REQUIREMENT , line ) );
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
        else if( line.startsWith( VALUE_PREDICATE ) )
        {
            getElement().setPredicate( getValue( VALUE_PREDICATE , line ) );
        }
        else if( line.startsWith( RATIONALE ) )
        {
            getElement().setRationale( getValue( RATIONALE , line ) );
        }
        else if( line.startsWith( MITIGATES ) )
        {
            getElement().setMitigates( getValue( MITIGATES , line ) );
        }
        else if( line.startsWith( REFINES ) )
        {
            getElement().setRefines( getValue( REFINES , line ) );
        }
        else if( line.startsWith( EVOLVES ) )
        {
            getElement().setEvolves( getValue( EVOLVES , line ) );
        }
        else if( line.startsWith( DECOMPOSES ) )
        {
            getElement().setDecomposes( getValue( DECOMPOSES , line ) );
        }
        else if( line.startsWith( INHERITS ) )
        {
            getElement().setDecomposes( getValue( INHERITS , line ) );
        }
        else if( line.startsWith( DROPPER ) )
        {
            getElement().setDropped( getValue( DROPPER , line ) );
        }
        else if( line.startsWith( DEVELOPMENT_STAKEHOLDER ) )
        {
            getElement().setDevelopmentStakeholder( getValue(DEVELOPMENT_STAKEHOLDER , line ) );
        }
        else if( line.startsWith( SEE_GOAL ) )
        {
            getElement().setSeeGoals( getValue( SEE_GOAL , line ) );
        }
        else if( line.startsWith( SEE_DOCUMENT ) )
        {
            getElement().setSeeDocuments( getValue( SEE_DOCUMENT , line ) );
        }
        else if( line.startsWith( SEE_REQUIREMENT ) )
        {
            getElement().setSeeDocuments( getValue( SEE_REQUIREMENT , line ) );
        }
        else if( line.startsWith( CHANGE_UNCERTAINTY ) )
        {
            getElement().setChangeUncertainty( getValue( CHANGE_UNCERTAINTY , line ) );
        }
        else if( line.startsWith( ReqspecProcessUtils.WHEN_EXPRESSION ) )
        {
            getElement().setWhenCondition( line );
        }
        else if( line.startsWith( ReqspecProcessUtils.CONSTANT ) )
        {
            getElement().getConstants().add( 
                ReqspecProcessUtils.getConstant( line ) 
            );
        }
        else if( line.startsWith( ReqspecProcessUtils.COMPUTE ) )
        {
            getElement().getComputeds().add( 
                ReqspecProcessUtils.getCompute( line ) 
            );
        }
        
        return i;
    }
    
}
