package org.osate.aadl.evaluator.reqspec;

import java.util.HashMap;
import java.util.Map;

public class Organization 
{
    public static final String ORG_STAKE_SEPARETOR = ".";
    
    private Reqspec parent;
    
    private String name;
    private final Map<String,Stakeholder> stakeholders;

    public Organization() 
    {
        this.stakeholders = new HashMap<>();
    }

    public Reqspec getParent()
    {
        return parent;
    }

    public void setParent( Reqspec parent )
    {
        this.parent = parent;
    }
    
    public String getName()
    {
        return name;
    }

    public Organization setName( String name )
    {
        this.name = name;
        return this;
    }

    public Map<String, Stakeholder> getStakeholders()
    {
        return stakeholders;
    }
    
    public Organization add( Stakeholder stakeholder )
    {
        stakeholders.put( stakeholder.getFullName() , stakeholder );
        return this;
    }
    
    public Stakeholder get( String name )
    {
        if( name.contains( ORG_STAKE_SEPARETOR ) )
        {
            return parent.getStakeholder( name );
        }
        else
        {
            return stakeholders.get( name );
        }
    }
    
}