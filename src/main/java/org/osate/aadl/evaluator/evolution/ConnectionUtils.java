package org.osate.aadl.evaluator.evolution;

import java.text.MessageFormat;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Connection;
import org.osate.aadl.evaluator.project.Property;
import org.osate.aadl.evaluator.project.Subcomponent;

public class ConnectionUtils 
{
    private static final String NAME  = "c{0}";
    private static final String VALUE = "{0} {1} {2} {3}";
    
    private ConnectionUtils()
    {
        // do nothing
    }
    
    // uma conexão existente pode ser mantido como está
    // uma conexão existente pode ser mantido, mas mudar de barramento
    // uma conexão existente pode ser alterado, mas mantido no barramento
    // uma conexão existente pode alterar o barramento e alterar seu valor
    
    // adicionar uma nova conexão
    
    // considerar que, se for um software, alterar também 
    // o software para a CPU correspondente
    
    public static Connection add( Component component , Binding binding )
    {
        Connection connection = new Connection();
        connection.setParent( component );
        
        setName ( component , connection );
        setValue( binding   , connection );
        
        if( connection.getSubcomponentB() == null )
        {
            System.out.println( "[ERROR] The subcomponentB " 
                + connection.getSubcomponentAndFeatureB()  
                + " in the connection " 
                + connection 
                + " is null." 
            );
            
            return connection;
        }
        
        component.add( connection );
        
        if( binding.getBus() == null 
            || binding.getBus().trim().isEmpty() )
        {
            return connection;
        }
        
        String process = connection.getSubcomponentB().isProcess()
            ? connection.getSubcomponentNameB()
            : connection.getSubcomponentNameA();
        
        // add the connection to bus
        Property p1 = BusUtils.find( component , binding.getBus() );
        BusUtils.add( p1 , connection.getName() );
        
        // add the process to CPU
        Property p2 = ProcessorUtils.find( component , binding.getCpu() );
        ProcessorUtils.add( p2 , process );
        
        return connection;
    }
    
    public static Connection change( Component component , Binding binding )
    {
        Connection connection = component.getConnection( binding.getConnection().getName() );
        setName ( component , connection );
        setValue( binding   , connection );
        
        if( binding.getBus() == null 
            || binding.getBus().trim().isEmpty() )
        {
            return connection;
        }
        
        // change the bus if is necessary
        Property p1 = BusUtils.find(component, connection.getName() );
        String bus  = BusUtils.getBusName( p1 );
        
        if( !binding.getBus().equalsIgnoreCase( bus ) )
        {
            if( BusUtils.remove( p1 , connection.getName() ).isEmpty() 
                && p1 != null )
            {
                p1.removeFromParent();
            }
            
            p1 = BusUtils.find( component , binding.getBus() );
            BusUtils.add( p1 , connection.getName() );
        }
        
        if( binding.getCpu() == null 
            || binding.getCpu().trim().isEmpty() )
        {
            return connection;
        }
        
        // change the CPU if is necessary
        Property p2 = ProcessorUtils.find(component, connection.getName() );
        String cpu  = ProcessorUtils.getProcessorName( p2 );
        
        if( !binding.getCpu().equalsIgnoreCase( cpu ) )
        {
            if( ProcessorUtils.remove( p2 , connection.getName() ).isEmpty() 
                && p1 != null )
            {
                p1.removeFromParent();
            }
            
            p2 = ProcessorUtils.find( component , binding.getBus() );
            ProcessorUtils.add( p2 , connection.getName() );
        }
        
        return connection;
    }
    
    public static void remove( Component component , Binding binding )
    {
        if( binding.getConnection() == null )
        {
            return ;
        }
        
        Connection connection = component.getConnection( binding.getConnection().getName() );
        String conName = connection.getName();
        
        // remove from the component
        component.getConnections().remove( conName );
        
        // remove from bus
        Property busP = BusUtils.find( component , conName );
        BusUtils.remove( busP , conName );
        
        // remove process form processor
        if( connection.getSubcomponentB() != null 
            && connection.getSubcomponentB().isProcess() )
        {
            Property cpuP = ProcessorUtils.find( component, connection.getSubcomponentNameB() );
            ProcessorUtils.remove( cpuP , conName );
        }
    }
    
    // ---------------- //
    // ---------------- //
    // ---------------- //
    
    public static boolean isToRemove( Binding binding )
    {
        return binding.getPartA() == null 
            || binding.getPartA().trim().isEmpty()
            || binding.getPartB() == null 
            || binding.getPartB().trim().isEmpty();
    }
    
    public static boolean isToChange( Binding binding )
    {
        return !isToRemove( binding ) 
            && binding.getConnection() != null;
    }
    
    public static boolean isToChangeTheBus( Component component , Binding binding )
    {
        if( binding.getConnection() == null )
        {
            return false;
        }
        
        String name = binding.getConnection().getName();
        Property property = BusUtils.find( component , name );
        String bus = BusUtils.getBusName( property );
        
        return bus != null && bus.equalsIgnoreCase( 
            binding.getBus() 
        );
    }
    
    // ---------------- //
    // ---------------- //
    // ---------------- //
    
    public static void setValue( Binding binding , Connection connection )
    {
        Subcomponent subA = connection.getParent().getSubcomponent( 
            BindingUtils.getSubcomponentName( binding.getPartA() ) 
        );
        
        String featureNameA = BindingUtils.getFeatureName( binding.getPartA() );
        
        if( subA == null )
        {
            return ;
        }
        
        boolean isBus = featureNameA == null
            ? subA.isBus()
            : subA.getComponent().getFeature( featureNameA ).isBus();
        
        String left  = binding.getPartA();
        String right = binding.getPartB();
        
        connection.setValue( MessageFormat.format( 
            VALUE , 
            isBus ? Connection.TYPE_BUS : Connection.TYPE_PORT ,
            left ,
            binding.isBidirect() ? Connection.CONNECTOR_BIDIRECT : Connection.CONNECTOR_UNIDIRECT ,
            right
        ) );
        
        // If PartA if INPUT and PartB is OUTPUT, change the order.
        if( connection.getFeatureA() != null 
            && (connection.getFeatureA().isEventIn()
                || connection.getFeatureA().isPortIn()) 
            && connection.getFeatureB() != null 
            && (connection.getFeatureB().isEventOut()
                || connection.getFeatureB().isPortOut()))
        {
            connection.setValue( MessageFormat.format( 
                VALUE , 
                isBus ? Connection.TYPE_BUS : Connection.TYPE_PORT ,
                right ,
                binding.isBidirect() ? Connection.CONNECTOR_BIDIRECT : Connection.CONNECTOR_UNIDIRECT ,
                left
            ) );
        }
    }
    
    public static String setName( Component component , Connection connection )
    {
        if( connection.getName() != null 
            && !connection.getName().trim().isEmpty() )
        {
            return connection.getName();
        }
        
        int counter = component.getConnectionsAll().size();
        String name;
        
        do
        {
            name = MessageFormat.format( NAME , counter );
        }
        while( component.getConnection( name ) != null );
        
        connection.setName( name );
        return name;
    }
    
}
