package org.osate.aadl.evaluator.file.reqspec;

import java.util.List;
import org.osate.aadl.evaluator.reqspec.SystemRequirements;

public class SystemRequirementsProcess extends ElementProcess<SystemRequirements>
{
    public static final String SYSTEM_REQUIREMENTS = "system requirements";
    public static final String GLOBAL_REQUIREMENTS = "global requirements";
    
    public static final String USE_CONSTANTS = "use constants";
    public static final String SEE_DOCUMENT = "see document";
    public static final String SEE_GOALS = "see goals";
    public static final String INCLUDE  = "include";
    
    public SystemRequirementsProcess( boolean global )
    {
        super( new SystemRequirements( global ) 
            , SEE_DOCUMENT 
            , SEE_GOALS 
            , INCLUDE 
            , ReqspecProcessUtils.COMPUTE
            , ReqspecProcessUtils.CONSTANT
            , SystemRequirementProcess.REQUIREMENT 
        );
    }
    
    @Override
    public int process( int start , List<String> lines )
    {
        System.out.println( "   [REQUIREMENT-LIST][PROCESS] start" );
        int i = super.process( start , lines );
        System.out.println( "   [REQUIREMENT-LIST][PROCESS] end!" );
        
        return i;
    }
    
    @Override
    public void setHeader( String line )
    {
        element.setName ( getName( 
            getElement().isGlobal() ? GLOBAL_REQUIREMENTS : SYSTEM_REQUIREMENTS , 
            line 
        ) );
        
        element.setTitle( getTitle( line ) );
        element.setTarget( getFor( line , null ) );
        element.setUseConstants( getName( USE_CONSTANTS , line ) );
    }

    @Override
    public int process( int i , List<String> lines , String line )
    {
        if( line.startsWith( SEE_DOCUMENT ) )
        {
            getElement().setSeeDocuments( getValue( SEE_DOCUMENT , line ) );
        }
        if( line.startsWith( SEE_GOALS ) )
        {
            getElement().setSeeGoals( getValue( SEE_GOALS , line ) );
        }
        else if( line.startsWith( SystemRequirementProcess.REQUIREMENT ) )
        {
            final SystemRequirementProcess p = new SystemRequirementProcess();
            i = p.process( i , lines );
            
            getElement().add( p.getElement() );
        }
        else if( line.startsWith( ReqspecProcessUtils.CONSTANT ) )
        {
            getElement().add( 
                ReqspecProcessUtils.getConstant( line ) 
            );
        }
        else if( line.startsWith( ReqspecProcessUtils.COMPUTE ) )
        {
            getElement().add( 
                ReqspecProcessUtils.getCompute( line ) 
            );
        }
        
        return i;
    }
    
}
