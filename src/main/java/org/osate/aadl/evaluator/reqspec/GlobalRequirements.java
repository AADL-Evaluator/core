package org.osate.aadl.evaluator.reqspec;

import java.util.LinkedList;
import java.util.List;

public class GlobalRequirements 
{
    private String useConstants;
    private final List<String> variables;
    private final List<SystemRequirement> requirements;
    private String include;
    private String seeDocuments;
    private String seeGoals;

    public GlobalRequirements()
    {
        this.variables = new LinkedList<>();
        this.requirements = new LinkedList<>();
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

    public List<String> getVariables()
    {
        return variables;
    }

    public List<SystemRequirement> getRequirements()
    {
        return requirements;
    }
    
}