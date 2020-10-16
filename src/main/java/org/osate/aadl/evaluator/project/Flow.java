package org.osate.aadl.evaluator.project;

public class Flow extends Declaration
{
    
    public Flow()
    {
        super( "flow" );
    }

    public Flow( String name , String value )
    {
        super( "flow" );
        setName( name );
        setValue( value );
    }
    
    @Override
    public void removeFromParent() 
    {
        if( getParent() == null )
        {
            return ;
        }
        
        getParent().getFlows().remove( getName() );
    }
    
}
