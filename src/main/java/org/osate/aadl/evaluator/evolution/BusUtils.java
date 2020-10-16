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

public class BusUtils 
{
    public static final String CONNECTION_BINDING = "actual_connection_binding";
    public static final String REFERENCE = "(reference ({0})) applies to";
    public static final String REFERENCE_END = "applies to";
    public static final String CONNECTION_SEPARATOR = ",";
    
    private BusUtils()
    {
        // do nothing
    }
    
    public static Map<String,List<String>> getProcessors( Component system )
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
            String bus = null;
            String cpu = null;
            
            if( compA.isBus() )
            {
                bus = compA.getName();
            }
            else if( compB.isBus() )
            {
                bus = compB.getName();
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
            
            if( !results.containsKey( bus ) )
            {
                results.put( bus , new LinkedList<String>() );
            }
            
            results.get( bus ).add( cpu );
        }
        
        return results;
    }
    
    public static List<String> getProcessorsByBus( Component system , String selected )
    {
        List<String> processors = new LinkedList<>();
        
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
            String bus = null;
            String cpu = null;
            
            if( compA.isBus() )
            {
                bus = compA.getName();
            }
            else if( compB.isBus() )
            {
                bus = compB.getName();
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
            
            if( bus.equalsIgnoreCase( selected ) )
            {
                processors.add( cpu );
            }
        }
        
        return processors;
    }
    
    // -------------------- //
    // -------------------- //
    // -------------------- //
    
    public static Property find( Component system , String connection )
    {
        for( Property property : system.getPropertiesAll() )
        {
            if( !property.getName().equalsIgnoreCase( CONNECTION_BINDING ) )
            {
                continue ;
            }
            
            Set<String> connections = getConnections( property );
            
            if( connections.contains( connection ) )
            {
                return property;
            }
        }
        
        return null;
    }
    
    public static Map<String,Set<String>> getConnection( Component system )
    {
        Map<String,Set<String>> connections = new HashMap<>();
        
        for( Property property : system.getPropertiesAll() )
        {
            if( !property.getName().equalsIgnoreCase( CONNECTION_BINDING ) )
            {
                continue ;
            }
            
            connections.put( 
                getBusName( property ) , 
                getConnections( property )
            );
        }
        
        return connections;
    }
    
    public static Set<String> getConnectionFromBus( Component system , String bus )
    {
        String reference = MessageFormat.format( REFERENCE , bus );
        
        for( Property property : system.getPropertiesAll() )
        {
            if( !property.getName().equalsIgnoreCase( CONNECTION_BINDING ) 
                || !property.getValue().startsWith( reference ) )
            {
                continue ;
            }
            
            return getConnections( property );
        }
        
        return null;
    }
    
    // -------------------- //
    // -------------------- //
    // -------------------- //
    
    public static Set<String> add( Property property , String connection )
    {
        if( property == null 
            || connection == null 
            || connection.trim().isEmpty() )
        {
            return null;
        }
        
        String name = getBusName( property );
        String reference = MessageFormat.format( REFERENCE , name );
        
        Set<String> connections = getConnections( property );
        connections.add( connection );
        
        property.setValue( reference + " " + toString( connections ) );
        return connections;
    }
    
    public static Set<String> remove( Property property , String connection )
    {
        if( property == null 
            || connection == null 
            || connection.trim().isEmpty() )
        {
            return new HashSet<>();
        }
        
        String name = getBusName( property );
        String reference = MessageFormat.format( REFERENCE , name );
        
        Set<String> connections = getConnections( property );
        connections.remove( connection );
        
        property.setValue( reference + " " + toString( connections ) );
        return connections;
    }
    
    // -------------------- //
    // -------------------- //
    // -------------------- //
    
    public static String getBusName( Property property )
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
    
    public static Set<String> getConnections( Property property )
    {
        // get all connections
        int indexOf = property.getValue().indexOf( REFERENCE_END ) 
            + REFERENCE_END.length();
        
        String connectionStr = property.getValue().substring( indexOf ).trim();
        Set<String> connections = new LinkedHashSet<>();
        
        for( String connection : connectionStr.split( CONNECTION_SEPARATOR ) )
        {
            connections.add( connection.trim() );
        }
        
        return connections;
    }
    
    public static int hasConnection( List<String> connections , String con )
    {
        for( int i = 0 ; i < connections.size() ; i++ )
        {
            if( connections.get( i ).equalsIgnoreCase( con ) )
            {
                return i;
            }
        }
        
        return -1;
    }
    
    public static String toString( Collection connections )
    {
        return connections
            .toString()
            .replace( "[" , " " )
            .replace( "]" , " " )
            .trim();
    }
    
}
