package org.osate.aadl.evaluator.automatic;

import java.util.Objects;

public class BusAndCpu implements Cloneable
{
    private final String bus;
    private final String cpu;
    
    public BusAndCpu( String bus , String cpu )
    {
        this.bus = bus;
        this.cpu = cpu;
    }

    public String getBus() 
    {
        return bus;
    }

    public String getCpu() 
    {
        return cpu;
    }

    @Override
    public String toString() 
    {
        return getBus() + " & " + getCpu();
    }

    @Override
    public boolean equals( Object obj )
    {
        if( obj == null )
        {
            return false;
        }
        
        return hashCode() == obj.hashCode();
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        
        hash = 73 * hash + Objects.hashCode( this.bus );
        hash = 73 * hash + Objects.hashCode( this.cpu );
        
        return hash;
    }

    @Override
    public BusAndCpu clone() 
    {
        return new BusAndCpu( bus , cpu );
    }
    
}