package org.osate.aadl.evaluator.file.reqspec;



import java.util.List;
import org.osate.aadl.evaluator.reqspec.Organization;

public class OrganizationProcess 
{
    public static final String ORGANIZATION = "organization";
    
    private final Organization organization;
    private final String[] words;
    
    public OrganizationProcess() 
    {
        organization = new Organization();
        
        words = new String[]{
            ORGANIZATION , 
            StakeholderProcess.STAKEHOLDER ,
            ReqspecProcess.BLOCK_END
        };
    }

    public int process( int start , List<String> lines )
    {
        int i;
        
        for( i = start ; i < lines.size() ; i++ )
        {
            String line = lines.get( i ).trim();
            
            if( line.startsWith( ORGANIZATION ) )
            {
                organization.setName(
                    ReqspecProcessUtils.getValue( ORGANIZATION , line )
                );
            }
            else if( line.startsWith( StakeholderProcess.STAKEHOLDER ) )
            {
                final StakeholderProcess p = new StakeholderProcess();
                i = p.process( i , lines );
                
                organization.add( p.getStakeholder() );
            }
            else if( line.startsWith( ReqspecProcess.BLOCK_END ) )
            {
                break ;
            }
        }
        
        return i;
    }
    
    public Organization getOrganization() 
    {
        return organization;
    }
    
}