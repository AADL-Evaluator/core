package org.osate.aadl.evaluator.reqspec;

import java.util.LinkedList;
import java.util.List;

public class ReqspecElement 
{
    private String name;
    private String title;
    private String target;              // document and section don't have
    private String description;
    private final List<String> issues;

    public ReqspecElement() 
    {
        this.issues = new LinkedList<>();
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getTarget()
    {
        return target;
    }

    public void setTarget( String target )
    {
        this.target = target;
    }
    
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public List<String> getIssues()
    {
        return issues;
    }
    
}
