package org.osate.aadl.evaluator.reqspec;

import java.util.Map;
import java.util.TreeMap;

public class DocumentOrSection extends ReqspecElement
{
    private Reqspec parent;
    
    private final boolean document;
    private final Map<String,ReqspecElement> goals;
    private final Map<String,ReqspecElement> requirements;
    
    public DocumentOrSection( boolean document ) 
    {
        this.document = document;
        this.goals = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        this.requirements = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
    }

    public Reqspec getParent()
    {
        return parent;
    }

    public void setParent( Reqspec parent )
    {
        this.parent = parent;
    }
    
    public boolean isDocument()
    {
        return document;
    }
    
    public boolean isGoal()
    {
        return requirements.isEmpty();
    }
    
    public Map<String,ReqspecElement> getGoals()
    {
        return goals;
    }

    public Map<String,ReqspecElement> getRequirements()
    {
        return requirements;
    }
    
}