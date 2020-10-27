package org.osate.aadl.evaluator.reqspec;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.osate.aadl.evaluator.project.Project;
import static org.osate.aadl.evaluator.reqspec.Organization.ORG_STAKE_SEPARETOR;

public class Reqspec 
{
    private final Project parent;
    
    private final Map<String,SystemRequirements> systems;
    private final Map<String,SystemRequirements> globals;
    private final Map<String,DocumentOrSection> documents;
    private final Map<String,DocumentOrSection> sections;
    private final Map<String,StakeholderGoals> goals;
    private final Map<String,Organization> organizations;

    public Reqspec( final Project project )
    {
        this.parent = project;
        
        this.systems = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.globals = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.documents = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.sections = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.goals = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.organizations = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
    }

    public Project getParent()
    {
        return parent;
    }
    
    public Map<String,SystemRequirements> getSystems()
    {
        return systems;
    }

    public Map<String,SystemRequirements> getGlobals()
    {
        return globals;
    }

    public Map<String,DocumentOrSection> getDocuments()
    {
        return documents;
    }

    public Map<String,DocumentOrSection> getSections()
    {
        return sections;
    }

    public Map<String,StakeholderGoals> getGoals()
    {
        return goals;
    }
    
    public Map<String,Organization> getOrganizations()
    {
        return organizations;
    }
    
    // -------------------------------------------------- //
    // -------------------------------------------------- //
    // -------------------------------------------------- //
    
    public Reqspec add( Organization org )
    {
        org.setParent( this );
        organizations.put( org.getName() , org );
        
        return this;
    }
    
    public Reqspec add( ReqspecElement elm )
    {
        if( elm instanceof SystemRequirements )
        {
            ((SystemRequirements) elm).setParent( this );
            
            if( ((SystemRequirements) elm).isGlobal() )
            {
                globals.put( elm.getName() , (SystemRequirements) elm );
            }
            else
            {
                systems.put( elm.getName() , (SystemRequirements) elm );
            }
        }
        else if( elm instanceof DocumentOrSection )
        {
            ((DocumentOrSection) elm).setParent( this );
            
            if( ((DocumentOrSection) elm).isDocument() )
            {
                documents.put( elm.getName() , (DocumentOrSection) elm );
            }
            else
            {
                sections.put( elm.getName() , (DocumentOrSection) elm );
            }
        }
        else if( elm instanceof StakeholderGoals )
        {
            ((StakeholderGoals) elm).setParent( this );
            goals.put( elm.getName() , (StakeholderGoals) elm );
        }
        
        return this;
    }
    
    public Stakeholder getStakeholder( String name )
    {
        String parts[] = name.split( ORG_STAKE_SEPARETOR );
        String orgName = parts[0].trim();
        String stkName = parts[1].trim();
        
        if( organizations.containsKey( orgName ) )
        {
            return organizations.get( orgName ).get( stkName );
        }
        else
        {
            return null;
        }
    }
    
    public ReqspecElement getGoal( String name )
    {
        String parts[] = name.split( "." );
        String groupName = parts[0].trim();
        String goalName  = parts[1].trim();
        
        if( documents.containsKey( groupName ) )
        {
            return documents.get( groupName ).getGoals().get( goalName );
        }
        else if( sections.containsKey( groupName ) )
        {
            return sections.get( groupName ).getGoals().get( goalName );
        }
        else if( goals.containsKey( groupName ) )
        {
            return goals.get( groupName ).getGoals().get( goalName );
        }
        else
        {
            return null;
        }
    }
    
    public ReqspecElement getRequirement( String name )
    {
        String parts[] = name.split( "." );
        String groupName = parts[0].trim();
        String reqName  = parts[1].trim();
        
        if( documents.containsKey( groupName ) )
        {
            return documents.get( groupName ).getRequirements().get( reqName );
        }
        else if( sections.containsKey( groupName ) )
        {
            return sections.get( groupName ).getRequirements().get( reqName );
        }
        else if( systems.containsKey( groupName ) )
        {
            return systems.get( groupName ).getRequirements().get( reqName );
        }
        else if( globals.containsKey( groupName ) )
        {
            return globals.get( groupName ).getRequirements().get( reqName );
        }
        else
        {
            return null;
        }
    }
    
    public List<SystemRequirements> getRequirementByComponent( String component )
    {
        List<SystemRequirements> results = new LinkedList<>();
        
        for( SystemRequirements reqs : systems.values() )
        {
            if( isEquals( component , reqs.getTarget() ) )
            {
                results.add( reqs );
            }
        }
        
        return results;
    }
    
    public List<SystemRequirement> getRequirementBySubcomponent( String component , String subcomponent )
    {
        List<SystemRequirement> results = new LinkedList<>();
        
        for( SystemRequirements reqs : systems.values() )
        {
            if( !isEquals( component , reqs.getTarget() ) )
            {
                continue ;
            }
            
            for( SystemRequirement req : reqs.getRequirements().values() )
            {
                if( isEquals( subcomponent , req.getTarget() ) )
                {
                    results.add( req );
                }
            }
        }
        
        return results;
    }
    
    private boolean isEquals( String name1 , String name2 )
    {
        return ((name1 == null || name1.trim().isEmpty()) && (name2 == null || name2.isEmpty()))
            || (name1 != null && name1.equalsIgnoreCase( name2 ) );
    }
    
}
