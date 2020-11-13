package org.osate.aadl.evaluator.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.osate.aadl.evaluator.project.Connection;
import org.osate.aadl.evaluator.project.Declaration;
import org.osate.aadl.evaluator.project.Feature;
import org.osate.aadl.evaluator.project.Flow;
import org.osate.aadl.evaluator.project.Property;
import org.osate.aadl.evaluator.project.Subcomponent;

public class DeclarationProcess 
{
    public static final String BLOCK_START = "{";
    public static final String BLOCK_END = "}";
    
    public static final String SUBCOMPONENT_SEPARATOR = ":";
    public static final String FEATURE_SEPARATOR = ":";
    public static final String FLOW_SEPARATOR = ":";
    public static final String CONNECTION_SEPARATOR = ":";
    public static final String PROPERTY_SEPARATOR = "=>";
    
    private final String type;
    private final List<String> lines;
    private int i;

    public DeclarationProcess( String type , List<String> lines , int i )
    {
        this.type = type;
        this.lines = lines;
        this.i = i;
    }
    
    public int getI()
    {
        return i;
    }
    
    public Declaration getDeclaration( String lower , String line )
    {
        line = process( lower , line );
        return newDeclaration( type , line );
    }
    
    public static Declaration newDeclaration( String type , String line )
    {
        if( ProjectFile.SUBCOMPONENTS.equals( type ) )
        {
            return newSubcomponent( line );
        }
        else if( ProjectFile.FEATURES.equals( type ) )
        {
            return newOther( line , new Feature() , FEATURE_SEPARATOR );
        }
        else if( ProjectFile.CONNECTIONS.equals( type ) )
        {
            return newOther( line , new Connection() , CONNECTION_SEPARATOR );
        }
        else if( ProjectFile.PROPERTIES.equals( type ) )
        {
            return newOther( line , new Property() , PROPERTY_SEPARATOR );
        }
        else if( ProjectFile.FLOWS.equals( type ) )
        {
            return newOther( line , new Flow() , FLOW_SEPARATOR );
        }
        else
        {
            return null;
        }
    }
    
    private static Subcomponent newSubcomponent( String line )
    {
        String l = line.trim();
        int index = l.indexOf( SUBCOMPONENT_SEPARATOR );
        
        String name = l.substring( 0 , index ).trim();
        String value = l.substring( 
            index + SUBCOMPONENT_SEPARATOR.length() ,  
            l.length() - 1 )
        .trim();
        
        if( !value.contains( BLOCK_START ) 
            || !value.contains( BLOCK_END ) )
        {
            return new Subcomponent( name , value );
        }
        
        // ------ //
        
        int index1 = value.indexOf( BLOCK_START );
        int index2 = value.lastIndexOf( BLOCK_END );
        
        String v1 = value.substring( 0 , index1 );
        String v2 = value.substring( index1 + BLOCK_START.length() , index2 );
        
        Subcomponent sub = new Subcomponent( name , v1 );
        sub.getProperties().addAll( DeclarationProcessUtils.convert( v2 ) );
        
        return sub;
    }
    
    private static Declaration newOther( String line , Declaration declaration , String separator )
    {
        try
        {
            String l = line.trim();
            int index = l.indexOf( separator );

            declaration.setName( 
                l.substring( 0 , index ).trim() 
            );

            declaration.setValue( 
                l.substring( index + separator.length() ,  l.length() - 1 ).trim() 
            );

            return declaration;
        }
        catch( java.lang.StringIndexOutOfBoundsException err )
        {
            System.out.println( "[ERROR] line: " + line );
            throw err;
        }
    }
    
    // ------------------------- //
    // ------------------------- // AUXILIAR
    // ------------------------- //
    
    private String process( String lower , String line )
    {
        Map<String,Integer> counter = new HashMap<>();
        
        boolean isEnd = isFindTheEnd( counter , lower );
        boolean contain = lower.contains( ";" );
        
        while( !contain || !isEnd )
        {
            String next = ProjectFileUtils.removeComment( 
                lines.get( ++i ) 
            );
            
            if( !contain )
            {
                contain = next.contains( ";" );
            }
            
            isEnd = isFindTheEnd( counter , next );
            
            line  += "\n" + next;
            lower += " " + next.trim();
        }
        
        return line;
    }
    
    private boolean isFindTheEnd( Map<String,Integer> counter , String line )
    {
        for( char c : line.toCharArray() )
        {
            if( Property.OBJECT_START.equals( c + "" ) )
            {
                counter.put( 
                    "object" , 
                    counter.getOrDefault( "object" , 0 ) + 1 
                );
            }
            else if( Property.OBJECT_END.equals( c + "" ) )
            {
                counter.put( 
                    "object" , 
                    counter.getOrDefault( "object" , 0 ) - 1 
                );
            }
            else if( BLOCK_START.equals( c + "" ) )
            {
                counter.put( 
                    "block" , 
                    counter.getOrDefault( "block" , 0 ) + 1 
                );
            }
            else if( BLOCK_END.equals( c + "" ) )
            {
                counter.put( 
                    "block" , 
                    counter.getOrDefault( "block" , 0 ) - 1 
                );
            }
        }
        
        for( Map.Entry<String,Integer> entry : new HashMap<>( counter ).entrySet() )
        {
            if( entry.getValue() <= 0 )
            {
                counter.remove( entry.getKey() );
            }
        }
        
        return counter.isEmpty();
    }
    
}