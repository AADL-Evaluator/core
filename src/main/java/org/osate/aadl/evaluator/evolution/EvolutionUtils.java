package org.osate.aadl.evaluator.evolution;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Connection;
import org.osate.aadl.evaluator.project.Declaration;
import org.osate.aadl.evaluator.project.Feature;
import org.osate.aadl.evaluator.project.Property;
import org.osate.aadl.evaluator.project.Subcomponent;

public class EvolutionUtils 
{
    
    private EvolutionUtils()
    {
        // do nothing
    }
    
    // --- //
    // --- //
    // --- //
    
    public static Component change( Evolution evolution ) throws Exception
    {
        Component cloned = evolution.getSystem().clone();
        
        if( evolution.getCandidates().isEmpty() 
            && evolution.getBindings().isEmpty() )
        {
            return cloned;
        }
        
        cloned = candidates( evolution , cloned );
        cloned = bindings( evolution , cloned );
        
        return cloned;
    }
    
    public static Component candidates( Evolution evolution , Component system )
    {
        Component cloned = system.clone();
        
        // remove as conexões antigas
        for( Declaration declaration : evolution.getDeclarations() )
        {
            //TODO: se a declaração estiver no pai ou no implementation?
            cloned.getSubcomponents().remove( 
                declaration.getName() 
            );
        }
        
        // adiciona as novas
        for( Candidate candidate : evolution.getCandidates() )
        {
            Subcomponent sub = SubcomponentUtils.add( 
                cloned , 
                candidate.getComponent() 
            );
            
            candidate.setSubComponentName( sub.getName() );
        }
        
        return cloned;
    }
    
    public static Component bindings( Evolution evolution , Component system ) throws Exception
    {
        Component cloned = system.clone();
        List<String> used = new LinkedList<>();
        
        for( Binding binding : evolution.getBindings() )
        {
            Binding bindingCloned = binding.clone();
            
            // it is necessary to avoid edit same connection from different bindings
            if( bindingCloned.getConnection() != null )
            {
                if( used.contains( bindingCloned.getConnection().getName() ) )
                {
                    bindingCloned.setConnection( null );
                }
                else
                {
                    used.add( bindingCloned.getConnection().getName() );
                }
            }
            
            if( ConnectionUtils.isToRemove( bindingCloned ) )
            {
                ConnectionUtils.remove( cloned , bindingCloned );
            }
            //else if( BindingUtils.isCompatibleWithWrapper( cloned , binding ) )
            else if( BindingUtils.isCompatible( cloned , bindingCloned ) == BindingUtils.TYPE_COMPATIBLE_WITH_WRAPPER )
            {
                wrapper( cloned , bindingCloned );
            }
            else if( ConnectionUtils.isToChange( bindingCloned ) )
            {
                ConnectionUtils.change( cloned , bindingCloned );
            }
            else
            {
                ConnectionUtils.add( cloned , bindingCloned );
            }
        }
        
        return cloned;
    }
    
    public static Component wrapper( final Component system , final Binding b )
    {
        Candidate candidate = new Candidate( 
            BindingWrapperUtils.create( system , b ) , 
            new LinkedList<>() 
        );
        
        Subcomponent sub = SubcomponentUtils.add( 
            system , 
            candidate.getComponent()
        );

        candidate.setSubComponentName( sub.getName() );

        // create a new binding
        Binding b0 = new Binding();
        b0.setPartA( sub.getName() + ".partB" );
        b0.setPartB( b.getPartB() );
        b0.setBidirect( b.isBidirect() );
        b0.setBus( b.getBus() );
        b0.setCpu( b.getCpu() );
        b0.setCandidateIsPartA( b0.isCandidateIsPartA() );

        // set here de wrapper
        Binding b1 = b.clone();
        b1.setPartB( sub.getName() + ".partA" );
        
        // add new bindings
        ConnectionUtils.add( system , b0 );
        
        if( b1.getConnection() != null )
        {
            ConnectionUtils.change( system , b1 );
        }
        else
        {
            ConnectionUtils.add( system , b1 );
        }
        
        return system;
    }
    
    // --- //
    // --- //
    // --- //
    
    public static List<String> diff( Component original , Evolution evolution ) throws Exception
    {
        Map<String,List<String>> results = diff( original , evolution.getSystemWidthChanges() );
        
        LinkedList<String> list = new LinkedList<>();
        
        if( !results.get( "added" ).isEmpty() )
        {
            list.add( "declaration added:\n " 
                + toString( results.get( "added" ) , "\n" )
            );
        }
        
        if( !results.get( "changed" ).isEmpty() )
        {
            list.add( "declaration changed:\n " 
                + toString( results.get( "changed" ) , "\n" )
            );
        }
        
        if( !results.get( "deleted" ).isEmpty() )
        {
            list.add( "declaration deleted:\n " 
                + toString( results.get( "deleted" ) , "\n" )
            );
        }
        
        if( !evolution.getComponents().isEmpty() )
        {
            list.add( "component edited:\n " 
                + toString( evolution.getComponents().keySet() 
                , "\n" )
            );
        }
        
        return list;
    }
    
    public static Map<String,List<String>> diff( Component original , Component copy )
    {
        LinkedList<String> addded  = new LinkedList<>();
        LinkedList<String> changed = new LinkedList<>();
        
        // ----- verify features
        for( Map.Entry<String,Feature> entry : copy.getFeatures().entrySet() )
        {
            Feature feature = original.getFeatures().get( entry.getKey() );
            
            if( feature == null )
            {
                addded.add( "    feature " + entry.getKey() );
            }
            else if( !feature.getValue().equalsIgnoreCase( entry.getValue().getValue() ) )
            {
                changed.add( "    feature " + feature.getName() );
            }
        }
        
        // ----- verify subcomponents
        for( Map.Entry<String,Subcomponent> entry : copy.getSubcomponents().entrySet() )
        {
            Subcomponent subcomponent = original.getSubcomponents().get( entry.getKey() );
            
            if( subcomponent == null )
            {
                addded.add( "    subcomponent " + entry.getKey() );
            }
            else if( !subcomponent.getValue().equalsIgnoreCase( entry.getValue().getValue() ) )
            {
                changed.add( "    subcomponent " + subcomponent.getName() );
            }
        }
        
        // ----- verify connections
        for( Map.Entry<String,Connection> entry : copy.getConnections().entrySet() )
        {
            Connection connection = original.getConnections().get( entry.getKey() );
            
            if( connection == null )
            {
                addded.add( "    connection " + entry.getKey() );
            }
            else if( !connection.getValue().equalsIgnoreCase( entry.getValue().getValue() ) )
            {
                changed.add( "    connection " + connection.getName() );
            }
        }
        
        // ----- verify properties
        for( Property p : copy.getProperties() )
        {
            List<Property> properties = original.getProperty( p.getName() );
            
            if( properties == null || properties.isEmpty() )
            {
                addded.add( "    property " + p.getName() );
            }
            else
            {
                /*
                TODO: precisa ver isso aqui melhor. Como identificar que houve alteração.
                
                for( Property property : properties )
                {
                    if( !property.getValue().equalsIgnoreCase( p.getValue() ) 
                        && property.getName().equalsIgnoreCase( p.getName() )
                        && property.getParent().getName().equalsIgnoreCase( p.getParent().getName() ) )
                    {
                        System.out.println( "p1: " + property.getValue() );
                        System.out.println( "p2: " + p.getValue() );
                        System.out.println( "---" );
                        
                        changed.add( "    property " + p.getName() );
                    }
                }
                */
            }
        }
        
        Map<String,List<String>> results = new LinkedHashMap<>();
        results.put( "added"   , addded  );
        results.put( "changed" , changed );
        results.put( "deleted" , getDeleted( original , copy ) );
        
        return results;
    }
    
    private static List<String> getDeleted( Component original , Component copy )
    {
        LinkedList<String> deleted  = new LinkedList<>();
        
        // ----- verify features
        for( Map.Entry<String,Feature> entry : original.getFeatures().entrySet() )
        {
            Feature feature = copy.getFeatures().get( entry.getKey() );
            
            if( feature == null )
            {
                deleted.add( "    feature " + entry.getKey() );
            }
        }
        
        // ----- verify subcomponents
        for( Map.Entry<String,Subcomponent> entry : original.getSubcomponents().entrySet() )
        {
            Subcomponent subcomponent = copy.getSubcomponents().get( entry.getKey() );
            
            if( subcomponent == null )
            {
                deleted.add( "    subcomponent " + entry.getKey() );
            }
        }
        
        // ----- verify connections
        for( Map.Entry<String,Connection> entry : original.getConnections().entrySet() )
        {
            Connection connection = copy.getConnections().get( entry.getKey() );
            
            if( connection == null )
            {
                deleted.add( "    connection " + entry.getKey() );
            }
        }
        
        // ----- verify properties
        for( Property p : original.getProperties() )
        {
            List<Property> properties = copy.getProperty( p.getName() );
            
            if( properties == null || properties.isEmpty() )
            {
                deleted.add( "    property " + p.getName() );
            }
        }
        
        return deleted;
    }
    
    public static String toString( Collection list )
    {
        return toString( list , ";" );
    }
    
    public static String toString( Collection list , String connector )
    {
        if( list == null || list.isEmpty() )
        {
            return "";
        }
        
        // --- //
        
        StringBuilder builder = new StringBuilder();
        boolean empty = true;
        
        for( Object item : list )
        {
            if( !empty )
            {
                builder.append( connector ); 
                builder.append( " " ); 
            }
            
            empty = false;
            builder.append( item );
        }
        
        return builder.toString();
    }
    
}
