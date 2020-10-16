package org.osate.aadl.evaluator.evolution;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Declaration;
import org.osate.aadl.evaluator.project.Property;

public class FuncionalityUtils 
{
    public static final String PROPERTY_FUNCIONALITY_1 = "Physical_Properties::Device_Functionality";
    public static final String PROPERTY_FUNCIONALITY_2 = "Device_property::Device_Functionality";
    
    private FuncionalityUtils()
    {
        // faz nada
    }
    
    public static Set<String> available( Evolution evolution )
    {
        return available( 
            FuncionalityUtils.list( evolution ).keySet() , 
            evolution.getCandidates() 
        );
    }
    
    public static Set<String> available( Collection<String> funcionalities , List<Candidate> candidates )
    {
        if( funcionalities == null || funcionalities.isEmpty() )
        {
            return new LinkedHashSet<>();
        }
        
        System.out.println( "      funcionality: " + funcionalities );
        
        Set<String> result = new LinkedHashSet<>();
        result.addAll( funcionalities );
        
        for( Candidate candidate : candidates )
        {
            System.out.println( "      [-] candidate: " 
                + candidate.getComponent().getName() 
                + "         funcionlatity: " 
                + candidate.getFuncionalities()
            );
            
            List<String> commons = new LinkedList<>( funcionalities );
            commons.retainAll( candidate.getFuncionalities().keySet() );
            
            result.removeAll( commons );
        }
        
        System.out.println( "      -----------------------------------" );
        System.out.println( "      funcionality avaiable: " + result );
        
        return result;
    }
    
    public static Map<String,List<Property>> list( Evolution evolution )
    {
        Map<String,List<Property>> funcionalities = new LinkedHashMap<>();
        
        for( Declaration declaration : evolution.getDeclarations() )
        {
            Component c = declaration.getComponent();
            
            if( c == null )
            {
                continue ;
            }
            
            funcionalities.putAll( list( c ) );
        }
        
        return funcionalities;
    }
    
    public static Map<String,List<Property>> list( Component component )
    {
        Map<String,List<Property>> funcionalities = new LinkedHashMap<>();
        
        for( Property p : component.getProperty( PROPERTY_FUNCIONALITY_1 , PROPERTY_FUNCIONALITY_2 ) )
        {
            if( p.getValueType() == Property.TYPE_ARRAY )
            {
                for( String value : p.getValueArray() )
                {
                    funcionalities.put( value.trim().toUpperCase() , new LinkedList<>() );
                }
            }
            else
            {
                String letter = p.getValue().contains( "," )
                    ? ","
                    : "+";
                
                for( String func : p.getValue().split( Pattern.quote( letter ) ) )
                {
                    funcionalities.put( func.trim().toUpperCase() , new LinkedList<>() );
                }
            }
        }
        
        for( String funcionality : funcionalities.keySet() )
        {
            String upper = funcionality.toUpperCase();
            
            for( Property property : component.getPropertiesAll() )
            {
                String name = property.getName().toUpperCase();
                
                if( name.startsWith( upper + "::" ) 
                    || name.startsWith( upper + "_PROPERTY::" )
                    || name.startsWith( upper + "_PROPERTIES::" ) )
                {
                    funcionalities.get( funcionality ).add( property );
                }
            }
        }
        
        return funcionalities;
    }
    
    public static Set<String> commons( Component component , Collection<String> funcionalities )
    {
        Set<String> commons = new HashSet<>();
        
        for( Property p : component.getProperty( PROPERTY_FUNCIONALITY_1 , PROPERTY_FUNCIONALITY_2 ) )
        {
            if( p.getValueType() == Property.TYPE_ARRAY )
            {
                for( String value : p.getValueArray() )
                {
                    value = value.trim().toUpperCase();
                    
                    if( funcionalities.contains( value ) )
                    {
                        commons.add( value );
                    }
                }
            }
            else
            {
                String letter = p.getValue().contains( "," )
                    ? ","
                    : "+";
                
                for( String value : p.getValue().split( Pattern.quote( letter ) ) )
                {
                    value = value.trim().toUpperCase();
                    
                    if( funcionalities.contains( value ) )
                    {
                        commons.add( value );
                    }
                }
            }
        }
        
        return commons;
    }
    
    public static boolean hasOneThisFuncionalities( Component c , Collection<String> funcionalities )
    {
        Collection<String> featureNames = list( c ).keySet();
        
        for( String f : funcionalities )
        {
            if( featureNames.contains( f ) )
            {
                return true;
            }
        }
        
        return false;
    }
    
}