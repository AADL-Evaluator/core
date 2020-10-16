package org.osate.aadl.evaluator.automatic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.osate.aadl.evaluator.evolution.Binding;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Connection;

public class AutomaticBinding extends Binding
{
    private Component system;
    private final List<BusAndCpu> busesAndCpus;

    public AutomaticBinding() 
    {
        busesAndCpus = new LinkedList<>();
    }

    public AutomaticBinding( Component system , Binding binding , List<BusAndCpu> busAndCpus ) 
    {
        setConnection( binding.getConnection() );
        setCandidateIsPartA( binding.isCandidateIsPartA() );
        setBidirect( binding.isBidirect() );
        setPartA( binding.getPartA() );
        setPartB( binding.getPartB() );
        setBus( binding.getBus() );
        setCpu( binding.getCpu() );
        
        this.system = system;
        this.busesAndCpus = new LinkedList<>();
        this.busesAndCpus.addAll( busAndCpus );
    }
    
    public AutomaticBinding( Connection connection , boolean candidateIsPartA ) 
    {
        super( connection , "" , candidateIsPartA );
        busesAndCpus = new LinkedList<>();
    }
    
    public AutomaticBinding( Connection connection , String bus , boolean candidateIsPartA ) 
    {
        super( connection , bus , candidateIsPartA );
        busesAndCpus = new LinkedList<>();
    }
    
    public AutomaticBinding( String partA , String partB ) 
    {
        super( partA , partB );
        busesAndCpus = new LinkedList<>();
    }
    
    // ------ //
    // ------ //
    // ------ //

    public void setSystem( Component system )
    {
        this.system = system;
    }

    public Component getSystem()
    {
        return system;
    }
    
    public List<BusAndCpu> getBusesAndCpus()
    {
        return busesAndCpus;
    }
    
    public List<AutomaticBinding> getBindinds()
    {
        if( busesAndCpus.isEmpty() )
        {
            return Arrays.asList( this.clone() );
        }
        
        List<AutomaticBinding> bindings = new LinkedList<>();
        
        for( BusAndCpu cpuAndBus : busesAndCpus )
        {
            AutomaticBinding cloned = this.clone();
            cloned.setBus( cpuAndBus.getBus() );
            cloned.setCpu( cpuAndBus.getCpu() );
            
            bindings.add( cloned );
        }
        
        return bindings;
    }
    
    @Override
    public AutomaticBinding clone()
    {
        AutomaticBinding cloned = new AutomaticBinding(
            getSystem() ,
            super.clone() ,
            new LinkedList<>()
        );
        
        for( BusAndCpu bc : getBusesAndCpus() )
        {
            cloned.getBusesAndCpus().add( bc.clone() );
        }
        
        return cloned;
    }
    
}