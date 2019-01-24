package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс практики.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "practics")
public class Practic implements Serializable {

    @Id
    @Column(name = "prc_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "prc_fullname", nullable = false, length = 255)
    private String fullName;

    @Column(name = "prc_shortname", nullable = false, length = 30)
    private String shortName;

    @Column(name = "prc_length", nullable = false)
    private float length;

    @Column(name = "prc_course", nullable = false)
    private int course;

    @Column(name = "prc_semester", nullable = false)
    private int semester;

    @ManyToOne
    @JoinColumn(name = "prc_plncode")
    private StudyPlan plan;

    @ManyToOne
    @JoinColumn(name = "prc_modcode")
    private StudyModule module;

    @Transient
    private int planCode;

    @Transient
    private int moduleCode;

    @PostLoad
    private void updateCodes() {
        planCode = plan.getId();
        if (null != module) {
            moduleCode = module.getId();
        }
    }

    public int getId() {
        return id;
    }

    public Practic() {
        // empty constructor
    }

    // Конструктор для копирования планов
    public Practic(final Practic sample) {
        fullName = sample.getFullName();
        shortName = sample.getShortName();
        length = sample.getLength();
        course = sample.getCourse();
        semester = sample.getSemester();
        plan = sample.getPlan();
        if (null != plan) {
            planCode = plan.getId();
        }
        module = sample.getModule();
        if (null != module) {
            moduleCode = module.getId();
        }
    }

    public StudyPlan getPlan() {
        return plan;
    }

    public void setPlan(StudyPlan plan) {
        this.plan = plan;
        if (null != plan) {
            planCode = plan.getId();
        } else {
            planCode = 0;
        }
    }

    public int getPlanCode() {
        return planCode;
    }

    public void setPlanCode(int planCode) {
        this.planCode = planCode;
    }

    public StudyModule getModule() {
        return module;
    }

    public void setModule(StudyModule module) {
        this.module = module;
        if (null != module) {
            moduleCode = module.getId();
        } else {
            moduleCode = 0;
        }
    }

    public int getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(int moduleCode) {
        this.moduleCode = moduleCode;
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

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
