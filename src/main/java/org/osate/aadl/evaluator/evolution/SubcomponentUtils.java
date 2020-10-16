package org.osate.aadl.evaluator.evolution;

import java.text.MessageFormat;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.ComponentPackage;
import org.osate.aadl.evaluator.project.Subcomponent;

public class SubcomponentUtils 
{
    private static final String VALUE = "{0} {1}";
    
    private SubcomponentUtils()
    {
        // do nothing
    }
    
    public static Subcomponent add( Component component , Component willBeInserted )
    {
        boolean isParentEquals = component.getParent().getName().equalsIgnoreCase( 
            willBeInserted.getParent().getName() 
        );
        
        String value = MessageFormat.format( VALUE , 
            willBeInserted.getType() , 
            isParentEquals ? willBeInserted.getName() 
                           : willBeInserted.getFullName()
        );
        
        Subcomponent sub = new Subcomponent( 
            name( component , willBeInserted ) , 
            value 
        );
        
        component.add( sub );
        
        return sub;
    }
    
    public static String name( Component component , Component willBeInserted )
    {
        String name = willBeInserted.getName();
        
        if( name.contains( ComponentPackage.PACKAGE_SEPARATOR ) )
        {
            name = name.substring( 
                name.lastIndexOf( ComponentPackage.PACKAGE_SEPARATOR )
                + ComponentPackage.PACKAGE_SEPARATOR.length() 
            );
        }
        
        if( name.contains( "." ) )
        {
            name = name.substring( 0 , name.indexOf( "." ) );
        }
        
        int counter = 0;
        
        while( component.getSubcomponent( name( name , counter ) ) != null )
        {
            counter++;
        }
        
        return name( name , counter );
    }
    
    public static String name( String name , int counter )
    {
        if( counter <= 0 )
        {
            return name;
        }
        else
        {
            return name + "_" + counter;
        }
    }
    
}
