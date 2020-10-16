package org.osate.aadl.evaluator.automatic;

import java.util.LinkedList;
import java.util.List;
import org.osate.aadl.evaluator.project.Declaration;

public class AutomaticOptions implements Cloneable
{
    private boolean selected;
    private Declaration declaration;
    private final List<String> funcionalities;
    private final List<AutomaticOption> components;
    
    public AutomaticOptions() 
    {
        this( null );
    }
    
    public AutomaticOptions( Declaration declaration ) 
    {
        this.declaration = declaration;
        this.components  = new LinkedList<>();
        this.funcionalities = new LinkedList<>();
    }
    
    public void setDeclaration( Declaration declaration )
    {
        this.declaration = declaration;
    }

    public boolean isSelected() 
    {
        return selected;
    }

    public void setSelected( boolean selected )
    {
        this.selected = selected;
    }
    
    public Declaration getDeclaration() 
    {
        return declaration;
    }

    public List<String> getFuncionalities() 
    {
        return funcionalities;
    }
    
    public List<AutomaticOption> getComponents()
    {
        return components;
    }
    
    public int getConfigurations()
    {
        if( components.isEmpty() )
        {
            return 0;
        }
        
        int total = 1;
        
        for( AutomaticOption option : components )
        {
            total *= option.getConfigurations();
        }
        
        return total;
    }

    @Override
    public AutomaticOptions clone()
    {
        AutomaticOptions clone = new AutomaticOptions( getDeclaration() );
        clone.setSelected( selected );
        clone.getFuncionalities().addAll( funcionalities );
        
        for( AutomaticOption option : getComponents() )
        {
            clone.getComponents().add( option.clone() );
        }
        
        return clone;
    }

    @Override
    public String toString() 
    {
        return getDeclaration().toString();
    }
    
}