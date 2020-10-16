package org.osate.aadl.evaluator.project;

import java.util.LinkedList;
import java.util.List;
import org.osate.aadl.evaluator.reqspec.SystemRequirement;

public class Subcomponent extends Declaration implements Cloneable
{
    private final List<Property> properties;
    
    public Subcomponent() 
    {
        super( "subcomponent" );
        properties = new LinkedList<>();
    }

    public Subcomponent( String name , String value ) 
    {
        super( "subcomponent" );
        setName( name );
        setValue( value );
        properties = new LinkedList<>();
    }
    
    public boolean isDevice()
    {
        return Component.TYPE_DEVICE.equalsIgnoreCase( getValueType() );
    }
    
    public boolean isProcess()
    {
        return Component.TYPE_PROCESS.equalsIgnoreCase( getValueType() );
    }
    
    public boolean isProcessor()
    {
        return Component.TYPE_PROCESSOR.equalsIgnoreCase( getValueType() );
    }
    
    public boolean isThread()
    {
        return Component.TYPE_THREAD.equalsIgnoreCase( getValueType() );
    }
    
    public boolean isBus()
    {
        return Component.TYPE_BUS.equalsIgnoreCase( getValueType() );
    }
    
    public boolean isData()
    {
        return Component.TYPE_DATA.equalsIgnoreCase( getValueType() );
    }
    
    public boolean isSystem()
    {
        return Component.TYPE_SYSTEM.equalsIgnoreCase( getValueType() );
    }
    
    public String getValueType()
    {
        if( getValue() == null 
            || getValue().trim().isEmpty() )
        {
            return null;
        }
        
        //process aadlbook::software::image_acquisition::image_acquisition.i
        
        //[0] process
        //[1] aadlbook::software::image_acquisition::image_acquisition.i
        
        // ---------- essa linha faz a mesma de baixo, com mais linha
        //int index = getValue().indexOf( " " );
        //getValue().substring( 0 , index );
        
        return getValue().split( " " )[ 0 ];
    }
    
    public List<Property> getProperties()
    {
        return properties;
    }
    
    public List<SystemRequirement> getRequirements()
    {
        return getParent()
            .getParent()
            .getParent()
            .getEspecification()
            .getRequirementBySubcomponent( 
                getParent().getFullName() , 
                getName() 
            );
    }
    
    @Override
    public void removeFromParent() 
    {
        if( getParent() == null )
        {
            return ;
        }
        
        getParent().getFeatures().remove( getName() );
    }
    
    @Override
    public Subcomponent clone()
    {
        return new Subcomponent( getName() , getValue() );
    }
    
}