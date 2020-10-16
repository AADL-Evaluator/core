package org.osate.aadl.evaluator.automatic;

import java.util.LinkedList;
import java.util.List;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Property;

/**
 * component: componente em analise.
 * 
 * properties: caso futuro. caso haja mais de uma configuração para o mesmo componente.
 * 
 * bindinds: é uma conjunto de composições de bindings.
 * [0] = é a primeira composição de bindings;
 * [1] = é a segunda compossição de bindings;
 * 
 * 
 * @author avld
 */
public class AutomaticOption implements Cloneable
{
    public static final String AVALIABLE = "avaliable_";
    
    private final Component component;
    private final List<Property> properties;
    private final List<List<AutomaticBinding>> bindings;
    
    public AutomaticOption() 
    {
        this( null );
    }
    
    public AutomaticOption( Component component ) 
    {
        this.component  = component;
        this.properties = new LinkedList<>();
        this.bindings   = new LinkedList<>();
        
        this.init();
    }
    
    private void init()
    {
        if( component == null )
        {
            return ;
        }
        
        for( Property property : component.getPropertiesAll() )
        {
            if( property.getName().toLowerCase().startsWith( AVALIABLE )
                && property.getValueType() == Property.TYPE_ARRAY )
            {
                properties.add( property );
            }
        }
    }
    
    public Component getComponent()
    {
        return component;
    }
    
    public List<Property> getProperties()
    {
        return properties;
    }

    public List<List<AutomaticBinding>> getBindings() 
    {
        return bindings;
    }
    
    public int getConfigurations()
    {
        int total = 1;
        
        for( Property property : properties )
        {
            total *= property.getValueArray().size();
        }
        
        return total;
    }
    
    /*
    public List<List<AutomaticBinding>> getBindingsComposed()
    {
        List<List<AutomaticBinding>> results = new LinkedList<>();
        
        // TODO: gambiarra para corrigir depois a composição de Bindings
        if( results.isEmpty() )
        {
            return Arrays.asList( getBindings() );
        }
        
        for( AutomaticBinding ab : getBindings() )
        {
            results = getBindingsComposed( results , ab );
        }
        
        return results;
    }
    
    private List<List<AutomaticBinding>> getBindingsComposed( List<List<AutomaticBinding>> before , AutomaticBinding ab )
    {
        List<List<AutomaticBinding>> results = new LinkedList<>();
        
        if( before.isEmpty() )
        {
            results.add( ab.getBindinds() );
        }
        else
        {
            for( AutomaticBinding binding : ab.getBindinds() )
            {
                for( List<AutomaticBinding> l : before )
                {
                    List<AutomaticBinding> cloned = new LinkedList<>( l );
                    cloned.add( binding );
                    
                    results.add( l );
                }
            }
        }
        
        return results;
    }
    */
    
    @Override
    public AutomaticOption clone()
    {
        AutomaticOption cloned = new AutomaticOption( component );
        
        for( List<AutomaticBinding> l : getBindings() )
        {
            List<AutomaticBinding> c = new LinkedList<>();
            
            for( AutomaticBinding ab : l )
            {
                c.add( ab.clone() );
            }
            
            cloned.getBindings().add( c );
        }
        
        return cloned;
    }

    @Override
    public String toString() 
    {
        return component.toString();
    }
    
}