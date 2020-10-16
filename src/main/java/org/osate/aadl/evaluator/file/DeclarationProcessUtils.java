package org.osate.aadl.evaluator.file;

import java.util.LinkedList;
import java.util.List;
import org.osate.aadl.evaluator.project.Property;

public class DeclarationProcessUtils 
{
    
    private DeclarationProcessUtils()
    {
        // do nothing
    }
    
    public static List<Property> convert( final String line )
    {
        List<Property> properties = new LinkedList<>();
        StringBuilder p = new StringBuilder();
        
        int counterObject = 0;
        int counterBlock = 0;
        
        for( char c : line.toCharArray() )
        {
                 if( c == '[' ) counterObject++;
            else if( c == ']' ) counterObject--;
            else if( c == '{' ) counterBlock++;
            else if( c == '}' ) counterBlock--;
                 
            p.append( c );
            
            if( c == ';' 
                && counterBlock <= 0 
                && counterObject <= 0 )
            {
                properties.add( (Property) DeclarationProcess.newDeclaration( 
                    ProjectFile.PROPERTIES , p.toString() 
                ) );
                
                p.setLength( 0 );
            }
        }
        
        try
        {
            if( !p.toString().trim().isEmpty() )
            {
                properties.add( (Property) DeclarationProcess.newDeclaration( 
                    ProjectFile.PROPERTIES , p.toString() 
                ) );

                p.setLength( 0 );
            }
        }
        catch( Exception err )
        {
            // do nothing
        }
        
        return properties;
    }
    
}