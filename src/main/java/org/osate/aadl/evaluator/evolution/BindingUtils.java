package org.osate.aadl.evaluator.evolution;

import org.osate.aadl.evaluator.automatic.AutomaticBinding;
import static org.osate.aadl.evaluator.evolution.BindingWrapperUtils.create;
import static org.osate.aadl.evaluator.evolution.BindingWrapperUtils.getObject;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Feature;
import org.osate.aadl.evaluator.project.Subcomponent;

public class BindingUtils 
{
    public static final int TYPE_CANDIDATE_NOT_DEFINED  = -2;
    public static final int TYPE_HARDWARE_SOFTWARE_NOT_DEFINED  = -1;
    public static final int TYPE_INCOMPATIBLE = 0;
    public static final int TYPE_COMPATIBLE   = 1;
    public static final int TYPE_COMPATIBLE_WITH_WRAPPER = 2;
    
    private BindingUtils()
    {
        // do nothing
    }
    
    public static String getSubcomponentName( String part )
    {
        if( part == null || part.trim().isEmpty() )
        {
            return null;
        }
        
        return part.contains( "." )
            ? part.substring( 0 , part.indexOf( "." ) )
            : part;
    }
    
    public static String getFeatureName( String part )
    {
        if( part == null 
            || part.trim().isEmpty() 
            || !part.contains( "." ) )
        {
            return null;
        }
        
        return part.substring( part.indexOf( "." ) + 1 ).trim();
    }
    
    public static boolean isAllPartsWereSetted( Binding binding )
    {
        return !(binding.getPartA() == null 
            || binding.getPartA().trim().isEmpty()
            || binding.getPartB() == null 
            || binding.getPartB().trim().isEmpty());
    }
    
    // ------ //
    // ------ //
    // ------ //
    
    public static String getCompatibleMessage( AutomaticBinding binding )
    {
        return getCompatibleMessage( binding.getSystem() , binding.getPartA() , binding.getPartB() );
    }
    
    public static String getCompatibleMessage( Component system , Binding binding )
    {
        return getCompatibleMessage( system , binding.getPartA() , binding.getPartB() );
    }
    
    public static String getCompatibleMessage( Component system , String partA , String partB )
    {
        try
        {
            switch( isCompatible( system , partA , partB ) )
            {
                case TYPE_CANDIDATE_NOT_DEFINED : return "Erro: candidate not defined.";
                case TYPE_HARDWARE_SOFTWARE_NOT_DEFINED : return "Erro: hardware/software not defined.";
                case TYPE_COMPATIBLE : return "Compatible";
                case TYPE_INCOMPATIBLE : return "Incompatible";
                case TYPE_COMPATIBLE_WITH_WRAPPER : return "Compatible with wrapper";
                default: return "unknwon";
            }
        }
        catch( Exception err )
        {
            return "Error: " + err.getMessage();
        }
    }
    
    // ------ //
    // ------ //
    // ------ //
    
    public static int isCompatible( AutomaticBinding binding ) throws Exception
    {
        return isCompatible( binding.getSystem() , binding.getPartA() , binding.getPartB() );
    }
    
    public static int isCompatible( Component system , Binding binding ) throws Exception
    {
        return isCompatible( system , binding.getPartA() , binding.getPartB() );
    }
            
    public static int isCompatible( Component system , String partA , String partB ) throws Exception
    {
        if( partA == null || partA.trim().isEmpty() )
        {
            return BindingUtils.TYPE_CANDIDATE_NOT_DEFINED;
        }
        if( partB == null || partB.trim().isEmpty() )
        {
            return BindingUtils.TYPE_HARDWARE_SOFTWARE_NOT_DEFINED;
        }
        
        String valueA = getCandidateLinked( system , partA ).toLowerCase();
        String valueB = getCandidateLinked( system , partB ).toLowerCase();
        
        return valueB.endsWith( valueA ) 
            ? TYPE_COMPATIBLE
            : isCompatibleWithWrapper( system , partA , partB )
                ? TYPE_COMPATIBLE_WITH_WRAPPER
                : TYPE_INCOMPATIBLE;
    }
    
    public static String getCandidateLinked( Component system , String part ) throws Exception
    {
        String subName = getSubcomponentName( part );
        String feaName = getFeatureName( part );
        
        if( subName == null )
        {
            throw new Exception( "The binding link to nobody!" );
        }
        
        Subcomponent subcomponent = system.getSubcomponent( subName );
        
        if( subcomponent == null )
        {
            throw new Exception( "The subcomponent " + subName + " not found!" );
        }
        
        Component c = subcomponent.getComponent();
        
        if( c == null )
        {
            throw new Exception( "The component " + subcomponent.getComponentReferenceName() + " not found!" );
        }
        
        String value = c.getFullName();
        
        if( feaName != null )
        {
            Component c1 = c.getFeature( feaName ).getComponent();
            
            if( c1 == null )
            {
                throw new Exception( "The component " + c.getFeature( feaName ).getComponentReferenceName() + " not found!" );
            }
            
            value = c1.getFullName();
        }
        
        return value;
    }
    
    public static boolean isCompatibleWithWrapper( Component system , Binding binding )
    {
        return isCompatibleWithWrapper( system , binding.getPartA() , binding.getPartB() );
    }
    
    public static boolean isCompatibleWithWrapper( Component system , String partA , String partB )
    {
        Feature featureA = create( getObject( system , partA ) );
        Feature featureB = create( getObject( system , partB ) );
        
        if( featureA == null 
            || featureB == null )
        {
            return false;
        }
        
        return (featureA.isBus() && featureB.isBus())
            || (featureA.isEventIn() && featureB.isEventOut())
            || (featureA.isEventOut() && featureB.isEventIn())
            || (featureA.isPortIn() && featureB.isPortOut())
            || (featureA.isPortOut() && featureB.isPortIn())
        ;
    }
    
}
