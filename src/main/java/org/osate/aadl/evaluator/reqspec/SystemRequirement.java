package org.osate.aadl.evaluator.reqspec;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SystemRequirement extends ReqspecElement
{
    private SystemRequirements parent;
    
    private String category;
    private final List<Variable> variables;
    private final List<Compute> computeds;
    private final List<Constant> constants;
    
    private String whenCondition;
    private String predicate;
    private String rationale;
    private String mitigates;
    private String refines;
    private String decomposes;
    private String inherits;
    private String evolves;
    private String dropped;
    private String developmentStakeholder;
    private String seeGoals;
    private String seeRequirements;
    private String seeDocuments;
    private String changeUncertainty;
    
    public SystemRequirement() 
    {
        this.variables = new LinkedList<>();
        this.computeds = new LinkedList<>();
        this.constants = new LinkedList<>();
    }

    public SystemRequirements getParent()
    {
        return parent;
    }

    public void setParent( SystemRequirements parent )
    {
        this.parent = parent;
    }
    
    public List<Compute> getComputeds()
    {
        return computeds;
    }

    public List<Constant> getConstants()
    {
        return constants;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory( String category )
    {
        this.category = category;
    }
    
    public List<Variable> getVariables()
    {
        return variables;
    }
    
    public String getWhenCondition() {
        return whenCondition;
    }

    public void setWhenCondition(String WhenCondition) {
        this.whenCondition = WhenCondition;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public String getMitigates() {
        return mitigates;
    }

    public void setMitigates(String mitigates) {
        this.mitigates = mitigates;
    }

    public String getRefines() {
        return refines;
    }

    public void setRefines(String refines) {
        this.refines = refines;
    }

    public String getDecomposes() {
        return decomposes;
    }

    public void setDecomposes(String decomposes) {
        this.decomposes = decomposes;
    }

    public String getInherits() {
        return inherits;
    }

    public void setInherits(String inherits) {
        this.inherits = inherits;
    }

    public String getEvolves() {
        return evolves;
    }

    public void setEvolves(String evolves) {
        this.evolves = evolves;
    }

    public String getDropped() {
        return dropped;
    }

    public void setDropped(String dropped) {
        this.dropped = dropped;
    }

    public String getDevelopmentStakeholder() {
        return developmentStakeholder;
    }

    public void setDevelopmentStakeholder(String developmentStakeholder) {
        this.developmentStakeholder = developmentStakeholder;
    }

    public String getSeeGoals() {
        return seeGoals;
    }

    public void setSeeGoals( String seeGoals )
    {
        this.seeGoals = seeGoals;
    }

    public String getSeeRequirements()
    {
        return seeRequirements;
    }

    public void setSeeRequirements( String seeRequirements )
    {
        this.seeRequirements = seeRequirements;
    }

    public String getSeeDocuments()
    {
        return seeDocuments;
    }

    public void setSeeDocuments( String seeDocuments )
    {
        this.seeDocuments = seeDocuments;
    }
    
    public String getChangeUncertainty() {
        return changeUncertainty;
    }

    public void setChangeUncertainty(String changeUncertainty) {
        this.changeUncertainty = changeUncertainty;
    }

    public Map<String,String> getConstantsMap()
    {
        Map<String,String> map = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        
        for( Constant constant : constants )
        {
            map.put( constant.getName() , constant.getValue() );
        }
        
        return map;
    }
    
}
