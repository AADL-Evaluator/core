package org.osate.aadl.evaluator.reqspec;

import java.util.Map;
import java.util.TreeMap;

public class StakeholderGoals extends ReqspecElement
{
    private Reqspec parent;
    private String useConstants;
    private String seeDocument;
    private final Map<String,Constant> constants;
    private final Map<String,StakeholderGoal> goals;

    public StakeholderGoals() 
    {
        this.constants = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.goals = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
    }

    public Reqspec getParent()
    {
        return parent;
    }

    public void setParent( Reqspec parent )
    {
        this.parent = parent;
    }
    
    public String getUseConstants()
    {
        return useConstants;
    }

    public void setUseConstants( String useConstants )
    {
        this.useConstants = useConstants;
    }

    public String getSeeDocument()
    {
        return seeDocument;
    }

    public void setSeeDocument( String seeDocument )
    {
        this.seeDocument = seeDocument;
    }

    public Map<String,Constant> getConstants()
    {
        return constants;
    }

    public Map<String,StakeholderGoal> getGoals() 
    {
        return goals;
    }
    
    public StakeholderGoals add( NameAndValue nameAndValue )
    {
        if( nameAndValue instanceof Constant )
        {
            constants.put( nameAndValue.getName() , (Constant) nameAndValue );
        }
        
        return this;
    }
    
    public StakeholderGoals add( StakeholderGoal goal )
    {
        goal.setParent( this );
        goals.put( goal.getName() , goal );
        
        return this;
    }
    
}