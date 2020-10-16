package org.osate.aadl.evaluator.project;

import org.osate.aadl.evaluator.evolution.Evolution;

public class ClassFactor 
{
    
    // ------------------------- MODIFICABILIDADE
    public static int componentRemoved = -1;
    public static int componentAdded = 1;
    public static int busChanged  = 1;
    public static int cpuChanged  = 1;
    public static int dataChanged = 1;
    public static int connectionChanged = 1;
    public static int connectionRemoved = 1;
    public static int connectionAdded = 1;
    public static int threadAffected = 1;
    
    private ClassFactor()
    {
        
    }
    
    public static int modificabilidade( Evolution evolution )
    {
        return 0;
    }
    
}