package org.osate.aadl.evaluator.reqspec;

import java.util.LinkedList;
import java.util.List;

public class StakeholderGoal extends ReqspecElement
{
    private StakeholderGoals parent;
    
    private String category;
    private final List<Constant> constants;
    private final List<String> whenConditions;
    private String rationale;
    private String refines;
    private String conflicts;
    private String evolves;
    private String dropped;
    private String stakeholder;
    private String seeGoals;
    private String seeDocuments;
    private String ChangeUncertainty;

    public StakeholderGoal()
    {
        constants = new LinkedList<>();
        whenConditions = new LinkedList<>();
    }

    public StakeholderGoals getParent()
    {
        return parent;
    }

    public void setParent( StakeholderGoals parent )
    {
        this.parent = parent;
    }

    public List<Constant> getConstants()
    {
        return constants;
    }

    public List<String> getWhenConditions()
    {
        return whenConditions;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public String getRefines() {
        return refines;
    }

    public void setRefines(String refines) {
        this.refines = refines;
    }

    public String getConflicts() {
        return conflicts;
    }

    public void setConflicts(String conflicts) {
        this.conflicts = conflicts;
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

    public String getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(String stakeholder) {
        this.stakeholder = stakeholder;
    }

    public String getSeeGoals() {
        return seeGoals;
    }

    public void setSeeGoals(String seeGoal) {
        this.seeGoals = seeGoal;
    }

    public String getSeeDocuments() {
        return seeDocuments;
    }

    public void setSeeDocuments(String seeDocument) {
        this.seeDocuments = seeDocument;
    }

    public String getChangeUncertainty() {
        return ChangeUncertainty;
    }

    public void setChangeUncertainty(String ChangeUncertainty) {
        this.ChangeUncertainty = ChangeUncertainty;
    }
    
}
