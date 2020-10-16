package org.osate.aadl.evaluator.reqspec;

public class Stakeholder 
{
    private Organization parent;
    
    private String name;
    private String fullName;
    private String title;
    private String description;
    private String role;
    private String email;
    private String phone;
    private String supervisor; // <Stakeholder>

    public Stakeholder() 
    {
        // do nothing
    }

    public Organization getParent()
    {
        return parent;
    }

    public void setParent( Organization parent )
    {
        this.parent = parent;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getFullName()
    {
        return fullName;
    }

    public Stakeholder setFullName( String fullName )
    {
        this.fullName = fullName;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public Stakeholder setTitle( String title )
    {
        this.title = title;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public Stakeholder setDescription( String description )
    {
        this.description = description;
        return this;
    }

    public String getRole()
    {
        return role;
    }

    public Stakeholder setRole( String role )
    {
        this.role = role;
        return this;
    }

    public String getEmail()
    {
        return email;
    }

    public Stakeholder setEmail( String email )
    {
        this.email = email;
        return this;
    }

    public String getPhone()
    {
        return phone;
    }

    public Stakeholder setPhone( String phone )
    {
        this.phone = phone;
        return this;
    }

    public String getSupervisor()
    {
        return supervisor;
    }

    public Stakeholder setSupervisor( String supervisor )
    {
        this.supervisor = supervisor;
        return this;
    }
    
    public Stakeholder getSupervisorStakeholder()
    {
        return parent.getStakeholders().get( supervisor );
    }
    
}