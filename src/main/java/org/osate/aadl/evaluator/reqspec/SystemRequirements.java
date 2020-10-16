package org.osate.aadl.evaluator.reqspec;

import java.util.Map;
import java.util.TreeMap;

public class SystemRequirements extends ReqspecElement
{
    private final boolean global;
    
    private Reqspec parent;
    
    private String useConstants;
    private final Map<String,Constant> constants;
    private final Map<String,Compute> computeds;
    private final Map<String,SystemRequirement> requirements;
    private String include;
    private String seeDocuments;
    private String seeGoals;

    public SystemRequirements( boolean global )
    {
        this.global = global;
        
        this.constants = new TreeMap<>();
        this.computeds = new TreeMap<>();
        this.requirements = new TreeMap<>();
    }

    public boolean isGlobal()
    {
        return global;
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
    
    public String getInclude()
    {
        return include;
    }

    public void setInclude( String include )
    {
        this.include = include;
    }

    public String getSeeDocuments()
    {
        return seeDocuments;
    }

    public void setSeeDocuments( String seeDocuments )
    {
        this.seeDocuments = seeDocuments;
    }

    public String getSeeGoals()
    {
        return seeGoals;
    }

    public void setSeeGoals( String seeGoals )
    {
        this.seeGoals = seeGoals;
    }

    public Map<String,Compute> getComputeds()
    {
        return computeds;
    }

    public Map<String,Constant> getConstants()
    {
        return constants;
    }
    
    public Map<String,SystemRequirement> getRequirements()
    {
        return requirements;
    }
    
    public SystemRequirements add( NameAndValue nameAndValue )
    {
        if( nameAndValue instanceof Constant )
        {
            constants.put( nameAndValue.getName() , (Constant) nameAndValue );
        }
        else if( nameAndValue instanceof Compute )
        {
            computeds.put( nameAndValue.getName() , (Compute) nameAndValue );
        }
        
        return this;
    }
    
    public SystemRequirements add( SystemRequirement requirement )
    {
        requirement.setParent( this );
        requirements.put( requirement.getName() , requirement );
        
        return this;
    }
    
}