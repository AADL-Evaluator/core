package org.osate.aadl.evaluator.evolution;

import org.osate.aadl.evaluator.project.Connection;

public class Binding implements Cloneable
{
    private Connection connection;
    private boolean bidirect;           // unidirect: ->        bidirect: <->
    private String partA;               // could be a Subcomponent or Feature
    private String partB;               // could be a Subcomponent or Feature
    private String bus;                 // PartA connect to PartB by bus
    private String cpu;                 // bus is connect to a cpu
    private boolean candidateIsPartA;
    
    public Binding() 
    {
        this( "" , "" );
    }

    public Binding( Connection connection , boolean candidateIsPartA ) 
    {
        this( connection , "" , candidateIsPartA );
    }
    
    public Binding( Connection connection , String bus , boolean candidateIsPartA ) 
    {
        this.connection = connection;
        
        if( candidateIsPartA )
        {
            this.partA = connection == null ? "" : connection.getSubcomponentAndFeatureA();
            this.partB = connection == null ? "" : connection.getSubcomponentAndFeatureB();
        }
        else
        {
            this.partA = connection == null ? "" : connection.getSubcomponentAndFeatureB();
            this.partB = connection == null ? "" : connection.getSubcomponentAndFeatureA();
        }
        
        this.bidirect = connection == null ? false : connection.isBidirect();
        this.bus = bus;
        this.candidateIsPartA = candidateIsPartA;
    }
    
    public Binding( String partA , String partB ) 
    {
        this.connection = null;
        this.partA = partA;
        this.partB = partB;
        this.bus = null;
        this.candidateIsPartA = true;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public Binding setConnection( Connection connection )
    {
        this.connection = connection;
        return this;
    }

    public boolean isBidirect()
    {
        return bidirect;
    }

    public Binding setBidirect( boolean bidirect )
    {
        this.bidirect = bidirect;
        return this;
    }

    public String getPartA()
    {
        return partA;
    }

    public Binding setPartA( String partA )
    {
        this.partA = partA;
        return this;
    }

    public String getPartB()
    {
        return partB;
    }

    public Binding setPartB( String partB )
    {
        this.partB = partB;
        return this;
    }

    public String getBus()
    {
        return bus;
    }

    public Binding setBus( String bus )
    {
        this.bus = bus;
        return this;
    }
    
    public String getCpu()
    {
        return cpu;
    }
    
    public Binding setCpu( String cpu )
    {
        this.cpu = cpu;
        return this;
    }

    public boolean isCandidateIsPartA()
    {
        return candidateIsPartA;
    }

    public Binding setCandidateIsPartA( boolean candidateIsPartA )
    {
        this.candidateIsPartA = candidateIsPartA;
        return this;
    }

    @Override
    public String toString() 
    {
        String con = getConnection() == null ? "" : (getConnection().getName() + ": ");
        
        return con + getPartA() + " "
            + (isBidirect() ? Connection.CONNECTOR_BIDIRECT : Connection.CONNECTOR_UNIDIRECT)
            + " " + getPartB();
    }
    
    @Override
    public Binding clone()
    {
        try
        {
            return (Binding) super.clone();
        }
        catch( Exception err )
        {
            Binding cloned = new Binding();
            cloned.setConnection( connection != null ? connection.clone() : null );
            cloned.setBus( bus );
            cloned.setBidirect( bidirect );
            cloned.setCpu( cpu );
            cloned.setPartA( partA );
            cloned.setPartB( partB );
            cloned.setCandidateIsPartA( candidateIsPartA );
            
            return cloned;
        }
    }
    
}