package org.osate.aadl.evaluator.evolution;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Connection;
import org.osate.aadl.evaluator.project.Property;
import org.osate.aadl.evaluator.project.Subcomponent;

public class ProcessorUtils 
{
    public static final String CONNECTION_BINDING = "actual_processor_binding";
    public static final String REFERENCE = "(reference ({0})) applies to";
    public static final String REFERENCE_END = "applies to";
    public static final String SEPARATOR = ",";
    
    private ProcessorUtils()
    {
        // do nothing
    }
    
    public static Map<String,List<String>> getBuses( Component system )
    {
        Map<String,List<String>> results = new HashMap<>();
        
        for( Connection connection : system.getConnectionsAll().values() )
        {
            // ------- get component
            Subcomponent compA = system.getSubcomponent( connection.getSubcomponentNameA() );
            Subcomponent compB = system.getSubcomponent( connection.getSubcomponentNameB() );
            
            if( compA == null || compB == null )
            {
                continue ;
            }
            
            // ------ select a bus and cpu
            Subcomponent bus = null;
            String cpu = null;
            
            if( compA.isBus() )
            {
                bus = compA;
            }
            else if( compB.isBus() )
            {
                bus = compB;
            }
            
            if( compA.isProcessor() )
            {
                cpu = connection.getSubcomponentNameA();
            }
            else if( compB.isProcessor() )
            {
                cpu = connection.getSubcomponentNameB();
            }
            
            // ------ ignore if not exist cpu and bus
            if( cpu == null || bus == null )
            {
                continue ;
            }
            
            if( !results.containsKey( cpu ) )
            {
                results.put( cpu , new LinkedList<String>() );
            }
            
            results.get( cpu ).add( bus.getName() );
        }
        
        return results;
    }
    
    public static List<String> getBusesByProcessor( Component system , String processor )
    {
        List<String> buses = new LinkedList<>();
        
        for( Connection connection : system.getConnectionsAll().values() )
        {
            // ------- get component
            Subcomponent compA = system.getSubcomponent( connection.getSubcomponentNameA() );
            Subcomponent compB = system.getSubcomponent( connection.getSubcomponentNameB() );
            
            if( compA == null || compB == null )
            {
                continue ;
            }
            
            // ------ select a bus and cpu
            Subcomponent bus = null;
            String cpu = null;
            
            if( compA.isBus() )
            {
                bus = compA;
            }
            else if( compB.isBus() )
            {
                bus = compB;
            }
            
            if( compA.isProcessor() )
            {
                cpu = connection.getSubcomponentNameA();
            }
            else if( compB.isProcessor() )
            {
                cpu = connection.getSubcomponentNameB();
            }
            
            // ------ ignore if not exist cpu and bus
            if( cpu == null || bus == null )
            {
                continue ;
            }
            
            if( cpu.equalsIgnoreCase( processor ) )
            {
                buses.add( bus.getName() );
            }
        }
        
        return buses;
    }
    
    // -------------------- //
    // -------------------- //
    // -------------------- //
    
    public static Property find( Component system , String process )
    {
        for( Property property : system.getPropertiesAll() )
        {
            if( !property.getName().equalsIgnoreCase( CONNECTION_BINDING ) )
            {
                continue ;
            }
            
            Set<String> processes = getProcesses( property );
            
            if( processes.contains( process ) )
            {
                return property;
            }
        }
        
        return null;
    }
    
    public static Map<String,Set<String>> getProcesses( Component system )
    {
        Map<String,Set<String>> processes = new HashMap<>();
        
        for( Property property : system.getPropertiesAll() )
        {
            if( !property.getName().equalsIgnoreCase( CONNECTION_BINDING ) )
            {
                continue ;
            }
            
            processes.put( 
                getProcessorName( property ) , 
                getProcesses( property )
            );
        }
        
        return processes;
    }
    
    public static Set<String> getProcessesByProcessor( Component system , String processor )
    {
        String reference = MessageFormat.format( REFERENCE , processor );
        
        for( Property property : system.getPropertiesAll() )
        {
            if( !property.getName().equalsIgnoreCase( CONNECTION_BINDING ) 
                || !property.getValue().startsWith( reference ) )
            {
                continue ;
            }
            
            return getProcesses( property );
        }
        
        return null;
    }
    
    // -------------------- //
    // -------------------- //
    // -------------------- //
    
    public static Set<String> add( Property property , String process )
    {
        if( property == null 
            || process == null 
            || process.trim().isEmpty() )
        {
            return null;
        }
        
        String name = getProcessorName( property );
        String reference = MessageFormat.format( REFERENCE , name );
        
        Set<String> processes = getProcesses( property );
        processes.add( process );
        
        property.setValue( reference + " " + toString( processes ) );
        
        return processes;
    }
    
    public static Set<String> remove( Property property , String process )
    {
        if( property == null 
            || process == null 
            || process.trim().isEmpty() )
        {
            return new HashSet<>();
        }
        
        String name = getProcessorName( property );
        String reference = MessageFormat.format( REFERENCE , name );
        
        Set<String> processes = getProcesses( property );
        processes.remove( process );
        
        property.setValue( reference + " " + toString( processes ) );
        
        return processes;
    }
    
    // -------------------- //
    // -------------------- //
    // -------------------- //
    
    public static String getProcessorName( Property property )
    {
        if( property == null 
            || property.getValue() == null )
        {
            return "";
        }
        
        return property.getValue().substring(
            property.getValue().lastIndexOf( "(" ) + 1 , 
            property.getValue().indexOf( ")" )
        );
    }
    
    public static Set<String> getProcesses( Property property )
    {
        // get all connections
        int indexOf = property.getValue().indexOf( REFERENCE_END ) 
            + REFERENCE_END.length();
        
        String connectionStr = property.getValue().substring( indexOf ).trim();
        Set<String> connections = new LinkedHashSet<>();
        
        for( String process : connectionStr.split( SEPARATOR ) )
        {
            connections.add( process.trim() );
        }
        
        return connections;
    }
    
    public static int hasProcess( List<String> processes , String process )
    {
        for( int i = 0 ; i < processes.size() ; i++ )
        {
            if( processes.get( i ).equalsIgnoreCase( process ) )
            {
                return i;
            }
        }
        
        return -1;
    }
    
    public static String toString( Collection<String> connections )
    {
        return connections
            .toString()
            .replace( "[" , " " )
            .replace( "]" , " " )
            .trim();
    }
    
}
