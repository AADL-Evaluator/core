package org.osate.aadl.evaluator.evolution;

import java.text.MessageFormat;
import org.osate.aadl.evaluator.automatic.AutomaticBinding;
import static org.osate.aadl.evaluator.evolution.BindingUtils.getFeatureName;
import static org.osate.aadl.evaluator.evolution.BindingUtils.getSubcomponentName;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Feature;
import org.osate.aadl.evaluator.project.Subcomponent;

public class BindingWrapperUtils 
{
    
    public static Component create( AutomaticBinding binding )
    {
        return create( 
            binding.getSystem() , 
            binding.getPartA() , 
            binding.getPartB() 
        );
    }
    
    public static Component create( Component system , Binding binding )
    {
        return create( 
            system , 
            binding.getPartA() , 
            binding.getPartB() 
        );
    }
    
    public static Component create( Component system , String partA , String partB )
    {
        Feature featureA = create( getObject( system , partA ) );
        Feature featureB = create( getObject( system , partB ) );
        
        if( featureA == null 
            || featureB == null )
        {
            return null;
        }
        
        String name = MessageFormat.format( 
            "wrapper_{0}_to_{1}" , 
            featureA.getName() ,
            featureB.getName()
        );
        
        featureA.setName( "partA" );
        featureB.setName( "partB" );
        
        // ---- //
        
        Component component = new Component();
        component.setType( featureA.isBus() 
            ? Component.TYPE_DEVICE 
            : Component.TYPE_PROCESS 
        );
        
        component.setParent( system.getParent() );
        component.setName( name );
        component.add( featureA );
        component.add( featureB );
        
        system.getParent().add( component );
        
        return component;
    }
    
    /**
     * It could return a Component or a Feature of component. 
     * 
     * @param system        system in question
     * @param part          what you want to analyse
     * @return              return a component or a feature
     */
    public static Object getObject( Component system , String part )
    {
        String subName = getSubcomponentName( part );
        String feaName = getFeatureName( part );
        
        if( subName == null ) return null;
        
        Subcomponent subcomponent = system.getSubcomponent( subName );
        
        if( subcomponent == null ) return null;
        
        Component c = subcomponent.getComponent();
        
        if( c == null ) return null;
        
        return feaName == null 
            ? c
            : c.getFeature( feaName );
    }
    
    public static Feature create( Object object )
    {
        if( object == null )
        {
            return null;
        }
        else if( object instanceof Component )
        {
            return create( (Component) object );
        }
        else if( object instanceof Feature )
        {
            return create( (Feature) object );
        }
        else
        {
            return null;
        }
    }
    
    public static Feature create( Component object )
    {
        if( Component.TYPE_BUS.equalsIgnoreCase( object.getType() ) )
        {
            return new Feature( 
                object.getName() , 
                Feature.BUS + " " + object.getFullName()
            );
        }
        else
        {
            return null;
        }
    }
    
    public static Feature create( Feature object )
    {
        Feature feature = object.clone();
        
        if( feature.getValue().contains( "in data" ) )
        {
            feature.setValue( 
                feature.getValue().replace( "in data" , "out data" ) 
            );
        }
        else if( feature.getValue().contains( "out data" ) )
        {
            feature.setValue( 
                feature.getValue().replace( "out data" , "in data" ) 
            );
        }
        else if( feature.getValue().contains( "in event" ) )
        {
            feature.setValue( 
                feature.getValue().replace( "in event" , "out event" ) 
            );
        }
        else if( feature.getValue().contains( "out event" ) )
        {
            feature.setValue( 
                feature.getValue().replace( "out event" , "in event" ) 
            );
        }
        
        return feature;
    }
    
}
