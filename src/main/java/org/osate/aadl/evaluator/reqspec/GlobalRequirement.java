package org.osate.aadl.evaluator.reqspec;

import java.util.LinkedList;
import java.util.List;

public class GlobalRequirement extends ReqspecElement
{
    private final List<String> variables;
    private String WhenCondition;
    private String Predicate;
    private String rationale;
    private String mitigates;
    private String refines;
    private String decomposes;
    private String inherits;
    private String evolves;
    private String dropped;
    private String developmentStakeholder;
    private String goals;
    private String requirements;
    private String documents;
    private String changeUncertainty;

    public GlobalRequirement() 
    {
        this.variables = new LinkedList<>();
    }

    public List<String> getVariables()
    {
        return variables;
    }
    
    public String getWhenCondition() {
        return WhenCondition;
    }

    public void setWhenCondition(String WhenCondition) {
        this.WhenCondition = WhenCondition;
    }

    public String getPredicate() {
        return Predicate;
    }

    public void setPredicate(String Predicate) {
        this.Predicate = Predicate;
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

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getChangeUncertainty() {
        return changeUncertainty;
    }

    public void setChangeUncertainty(String changeUncertainty) {
        this.changeUncertainty = changeUncertainty;
    }
    
}
