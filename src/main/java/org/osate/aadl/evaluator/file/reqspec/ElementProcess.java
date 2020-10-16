package org.osate.aadl.evaluator.file.reqspec;

import java.util.List;
import org.osate.aadl.evaluator.reqspec.ReqspecElement;

public abstract class ElementProcess<T extends ReqspecElement>
{
    public static final char TITLE = '"';
    public static final String FOR = " for ";
    
    public static final String DESCRIPTION = "description";
    public static final String DESCRIPTION_THIS = "description this";
    public static final String ISSUES = "issues";
    
    protected T element;
    protected String[] words;

    public ElementProcess( T element , String... words )
    {
        this.element = element;
        
        this.words = ReqspecProcessUtils.concat( 
            words , 
            new String[]{ DESCRIPTION , ISSUES , ReqspecProcess.BLOCK_END } 
        );
    }
    
    public int process( int start , List<String> lines )
    {
        int i = processHeader( start , lines ) + 1;
        
        for(  ; i < lines.size() ; i++ )
        {
            LineProcess lp = new LineProcess( words );
            i = lp.process( i , lines );
            
            String line = lp.toString();
            
            System.out.print( (i < 10 ? " " : "") + i );
            System.out.println( "  " + line );
            
            if( is( line , DESCRIPTION_THIS ) )
            {
                element.setDescription( getValue( DESCRIPTION_THIS , line ) );
            }
            else if( is( line , DESCRIPTION ) )
            {
                element.setDescription( getValue( DESCRIPTION , line ) );
            }
            else if( is( line , ISSUES ) )
            {
                element.getIssues().add( getValue( ISSUES , line ) );
            }
            else if( line.startsWith( ReqspecProcess.BLOCK_END ) )
            {
                break ;
            }
            else
            {
                i = process( i , lines , line );
            }
        }
        
        return i;
    }
    
    public int processHeader( int start , List<String> lines )
    {
        int i;
        
        StringBuilder header = new StringBuilder();
        boolean insideInString = false;
        boolean finished = false;
        
        for( i = start ; i < lines.size() ; i++ )
        {
            String line = lines.get( i ).trim();
            header.append( " " );
            
            for( char c : line.toCharArray() )
            {
                if( c == '"' )
                {
                    insideInString = !insideInString;
                }
                
                if( c == ReqspecProcess.BLOCK_START.charAt( 0 ) 
                    && !insideInString )
                {
                    finished = true;
                    break;
                }
                
                header.append( c );
            }
            
            if( finished )
            {
                break ;
            }
        }
        
        System.out.print( (i < 10 ? " " : "") + i );
        System.out.println( "  [HEADER]  " + header );
        
        setHeader( header.toString() );
        
        return i;
    }
    
    public abstract void setHeader( String line );
    public abstract int process( int i , List<String> lines , String line );
    
    protected boolean is( String line , String startWidth )
    {
        return line.startsWith( startWidth );
    }
    
    protected String getValue( String removeIt , String line )
    {
        return ReqspecProcessUtils.getValue( removeIt , line );
    }
    
    protected String getName( String removeItFirst , String line )
    {
        String name = line.substring( removeItFirst.length() + 1 ).trim();
        
        name = name.contains( " " ) 
            ? name.substring( 0 , name.indexOf( " " ) )
            : name;
        
        return name;
    }
    
    protected String getTitle( String line )
    {
        if( !line.contains( "\"" ) )
        {
            return null;
        }
        
        return line.substring( 
            line.indexOf( "\"" ) + 1 , 
            line.lastIndexOf( "\"" ) 
        );
    }
    
    protected String getFor( String line , String limit )
    {
        if( line.indexOf( FOR ) <= line.lastIndexOf( "\"" ) )
        {
            return null;
        }
        
        int i1 = line.indexOf( FOR ) + FOR.length();
        
        return limit != null && limit.isEmpty() && line.contains( limit ) 
            ? line.substring( i1 , line.indexOf( limit ) ).trim()
            : line.substring( i1 ).trim();
    }
    
    public T getElement()
    {
        return element;
    }
    
}
