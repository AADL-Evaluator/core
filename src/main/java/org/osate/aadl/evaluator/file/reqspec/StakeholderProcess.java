package org.osate.aadl.evaluator.file.reqspec;



import java.util.List;
import org.osate.aadl.evaluator.reqspec.Stakeholder;

public class StakeholderProcess 
{
    public static final String STAKEHOLDER  = "stakeholder";
    
    public static final String FULL_NAME  = "full name";
    public static final String TITLE  = "title";
    public static final String DESCRIPTION  = "description";
    public static final String ROLE  = "role";
    public static final String EMAIL  = "email";
    public static final String PHONE  = "phone";
    public static final String SUPERVISOR  = "supervisor";
    
    private final Stakeholder stakeholder;
    
    public StakeholderProcess() 
    {
        stakeholder = new Stakeholder();
    }
    
    public int process( int start , List<String> lines )
    {
        int i;
        
        for( i = start ; i < lines.size() ; i++ )
        {
            String line = lines.get( i ).trim();
            
            if( line.startsWith( STAKEHOLDER ) )
            {
                String name = ReqspecProcessUtils.getValue( STAKEHOLDER , line ).trim();
                
                if( name.endsWith( "[" ) )
                {
                    stakeholder.setName( 
                        name.substring( 0 , name.length() - 1 ).trim() 
                    );
                }
                else
                {
                    stakeholder.setName( name );
                }
            }
            else if( line.startsWith( FULL_NAME ) )
            {
                stakeholder.setFullName(
                    ReqspecProcessUtils.getValue( FULL_NAME , line ) 
                );
            }
            else if( line.startsWith( TITLE ) )
            {
                stakeholder.setTitle(
                    ReqspecProcessUtils.getValue( TITLE , line ) 
                );
            }
            else if( line.startsWith( DESCRIPTION ) )
            {
                stakeholder.setDescription(
                    ReqspecProcessUtils.getValue( DESCRIPTION , line ) 
                );
            }
            else if( line.startsWith( ROLE ) )
            {
                stakeholder.setRole(
                    ReqspecProcessUtils.getValue( ROLE , line ) 
                );
            }
            else if( line.startsWith( EMAIL ) )
            {
                stakeholder.setEmail(
                    ReqspecProcessUtils.getValue( EMAIL , line ) 
                );
            }
            else if( line.startsWith( PHONE ) )
            {
                stakeholder.setPhone(
                    ReqspecProcessUtils.getValue( PHONE , line ) 
                );
            }
            else if( line.startsWith( SUPERVISOR ) )
            {
                stakeholder.setSupervisor(
                    ReqspecProcessUtils.getValue( SUPERVISOR , line ) 
                );
            }
            else if ( line.startsWith( ReqspecProcess.BLOCK_END ) )
            {
                break ;
            }
        }
        
        return i;
    }
    
    public Stakeholder getStakeholder() 
    {
        return stakeholder;
    }
    
}