package org.osate.aadl.evaluator.file.reqspec;

import java.util.List;

public class LineProcess 
{
    private final StringBuilder line;
    private final String[] words;

    public LineProcess( String[] words ) 
    {
        this.line  = new StringBuilder();
        this.words = words;
    }
    
    public int process( int i , List<String> lines )
    {
        String l = ReqspecProcessUtils.removeComment( 
            lines.get( i ).trim() 
        );
        
        line.append( l );
        
        if( ReqspecProcess.BLOCK_END.equals( l ) 
            || l.endsWith( ReqspecProcess.BLOCK_START ) )
        {
            return i;
        }
        
        while( isToContinue( i + 1 , lines ) )
        {
            line.append( "\n" ).append( 
                ReqspecProcessUtils.removeComment( lines.get( ++i ) )
            );
        }
        
        return i;
    }
    
    private boolean isToContinue( int i , List<String> lines )
    {
        return i < lines.size() 
            && !startWith( lines.get( i ).trim() );
    }
    
    private boolean startWith( String line )
    {
        for( String word : words )
        {
            if( line.startsWith( word ) )
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public String toString() 
    {
        return line.toString().trim();
    }
    
}
