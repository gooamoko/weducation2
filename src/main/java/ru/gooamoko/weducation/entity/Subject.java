package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс дисциплины или междисциплинарного курса.
 */
@Entity
@Table(name = "subjects")
public class Subject implements Serializable {

    @Id
    @Column(name = "sub_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sub_number", nullable = false)
    private int number;

    @Column(name = "sub_fullname", nullable = false)
    private String fullName;

    @Column(name = "sub_shortname")
    private String shortName;

    @ManyToOne
    @JoinColumn(name = "sub_plncode", nullable = false)
    private StudyPlan plan;

    @Transient
    private int planCode;

    @ManyToOne
    @JoinColumn(name = "sub_modcode")
    private StudyModule module;

    @Transient
    private int moduleCode;

    private void updatePlanCode() {
        planCode = 0;
        if (plan != null) {
            planCode = plan.getId();
        }
    }

    private void updateModuleCode() {
        moduleCode = 0;
        if (module != null) {
            moduleCode = module.getId();
        }
    }

    @PostLoad
    private void updateCodes() {
        updatePlanCode();
        updateModuleCode();
    }

    public int getId() {
        return id;
    }

    public Subject() {
        // empty constructor
    }

    // Конструктор для копирования учебных планов
    public Subject(final Subject sample) {
        fullName = sample.getFullName();
        shortName = sample.getShortName();
        plan = sample.getPlan();
        if (null != plan) {
            planCode = plan.getId();
        }
        module = sample.getModule();
        if (null != module) {
            moduleCode = sample.getModuleCode();
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public StudyPlan getPlan() {
        return plan;
    }

    public void setPlan(StudyPlan plan) {
        this.plan = plan;
        updatePlanCode();
    }

    public StudyModule getModule() {
        return module;
    }

    public void setModule(StudyModule module) {
        this.module = module;
        updateModuleCode();
    }

    public int getPlanCode() {
        return planCode;
    }

    public void setPlanCode(int planCode) {
        this.planCode = planCode;
    }

    public int getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(int moduleCode) {
        this.moduleCode = moduleCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
