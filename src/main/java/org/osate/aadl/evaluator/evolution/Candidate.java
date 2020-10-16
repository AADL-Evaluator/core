package org.osate.aadl.evaluator.evolution;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Property;

public class Candidate implements Cloneable
{
    private String subComponentName;
    private Component component;
    private final Map<String,List<Property>> funcionalities;

    public Candidate() 
    {
        this.funcionalities = new LinkedHashMap<>();
    }

    public Candidate( Component component , Map<String,List<Property>> funcionalites ) 
    {
        this.component = component;
        this.funcionalities = new LinkedHashMap<>( funcionalites );
    }

    public Candidate( Component component , Collection<String> funcionalites ) 
    {
        this.component = component;
        this.funcionalities = FuncionalityUtils.list( component );
        
        for( String f : new HashSet<>( this.funcionalities.keySet() ) )
        {
            if( !funcionalites.contains( f ) )
            {
                this.funcionalities.remove( f );
            }
        }
    }

    public void setSubComponentName( String subComponentName )
    {
        this.subComponentName = subComponentName;
    }

    public String getSubComponentName()
    {
        return subComponentName;
    }
    
    public void setComponent( Component component )
    {
        this.component = component;
    }

    public Component getComponent()
    {
        return component;
    }
    
    public Map<String,List<Property>> getFuncionalities()
    {
        return funcionalities;
    }
    
    public String getFuncionalitiesToString()
    {
        return EvolutionUtils.toString( funcionalities.keySet() , "," );
    }

    @Override
    public String toString() 
    {
        return component.getName() 
            + " " 
            + getFuncionalitiesToString();
    }
    
    @Override
    public Candidate clone()
    {
        Candidate cloned = new Candidate( 
            component , 
            funcionalities
        );
        
        cloned.setSubComponentName( getSubComponentName() );
        
        return cloned;
    }
    
}