package org.osate.aadl.evaluator.automatic;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.osate.aadl.evaluator.evolution.Binding;
import org.osate.aadl.evaluator.evolution.Candidate;
import org.osate.aadl.evaluator.evolution.Evolution;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Declaration;

public class AutomaticEvolution extends Evolution
{
    public static final int STATUS_ACCEPTED = 0;
    public static final int STATUS_WARNING  = 1;
    public static final int STATUS_IGNORED  = 2;
    public static final int STATUS_DELETED  = 3;
    
    private int number;
    private int status;
    private final List<String> messages;
    
    public AutomaticEvolution( Component original )
    {
        super( original );
        this.messages = new LinkedList<>();
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber( int number )
    {
        this.number = number;
    }
    
    public List<String> getMessages()
    {
        return messages;
    }
    
    public int getStatus()
    {
        return status;
    }

    public void setStatus( int status )
    {
        this.status = status;
    }
    
    public String getStatusString()
    {
        switch( status )
        {
            case STATUS_ACCEPTED: return "ACCEPTED";
            case STATUS_DELETED: return "DELETED";
            case STATUS_WARNING: return "WARNING";
            case STATUS_IGNORED: return "IGNORED";
            default: return "";
        }
    }
    
    @Override
    public AutomaticEvolution clone() 
    {
        AutomaticEvolution e = new AutomaticEvolution( getSystem() );
        e.getMessages().addAll( new LinkedList<>( getMessages() ) );
        e.setNumber( getNumber() );
        e.setStatus( getStatus() );
        
        for( Map.Entry<String,Component> entry : getComponents().entrySet() )
        {
            e.getComponents().put( entry.getKey() , entry.getValue().clone() );
        }
        
        for( Binding binding : getBindings() )
        {
            e.getBindings().add( binding.clone() );
        }
        
        for( Candidate candidate : getCandidates() )
        {
            e.getCandidates().add( candidate.clone() );
        }
        
        for( Declaration declaration : getDeclarations() )
        {
            e.getDeclarations().add( declaration.clone() );
        }
        
        return e;
    }
    
}