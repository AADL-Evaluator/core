package org.osate.aadl.evaluator.evolution;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Declaration;

public class Evolution implements Cloneable
{
    private final Map<String,Component> components;     // components were edited, preserving the original
    private Component system;                           // a copy of a system
    
    private final List<Declaration> declarations;       // declarations that will be changed
    private final List<Candidate> candidates;           // candidates that will change the declarations
    private final List<Binding> bindings;                  // connections exists or will be created
    
    public Evolution( Component original )
    {
        this.system = original.clone();
        this.components = new TreeMap<>();
        this.declarations = new LinkedList<>();
        this.candidates = new LinkedList<>();
        this.bindings = new LinkedList<>();
    }
    
    public void setSystem( Component system )
    {
        this.system = system;
    }
    
    public Component getSystem()
    {
        return system;
    }
    
    public Component getSystemWidthChanges() throws Exception
    {
        return EvolutionUtils.change( this );
    }

    public List<Declaration> getDeclarations()
    {
        return declarations;
    }

    public List<Candidate> getCandidates()
    {
        return candidates;
    }
    
    public Map<String, Component> getComponents()
    {
        return components;
    }

    public List<Binding> getBindings()
    {
        return bindings;
    }
    
    @Override
    public Evolution clone() 
    {
        try
        {
            Evolution e = (Evolution) super.clone();
            e.setSystem( system.clone() );
            
            return e;
        }
        catch( Exception err )
        {
            return null;
        }
    }
    
    // -------------------------------------------
    // -------------------------------------------
    // -------------------------------------------
    
    @Override
    public int hashCode() 
    {
        return getCompose().hashCode();
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if( !(obj instanceof Evolution) && obj == null )
        {
            return false;
        }
        else if( this == obj )
        {
            return true;
        }
        
        return hashCode() == obj.hashCode();
    }
    
    private String getCompose()
    {
        TreeMap<String,String> compose = new TreeMap<>();
        
        for( Candidate candidade : candidates )
        {
            TreeSet<String> funs = new TreeSet<>( candidade.getFuncionalities().keySet() );
            
            TreeSet<String> list = new TreeSet<>();
            for( Binding binding : bindings )
            {
                list.add( binding.toString() );
            }
            
            compose.put(
                candidade.getComponent().getFullName() ,
                funs.toString() + " | " + list.toString()
            );
        }
        
        return compose.toString().toUpperCase();
    }
    
}