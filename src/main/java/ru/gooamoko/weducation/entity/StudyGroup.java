package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс учебной группы.
 */
@Entity
@Table(name = "groups")
public class StudyGroup implements Serializable {

    @Id
    @Column(name = "grp_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "grp_name", nullable = false, length = 20)
    private String name;

    @Column(name = "grp_master", length = 128)
    private String master;

    @Column(name = "grp_course", nullable = false)
    private int course;

    @Column(name = "grp_year", nullable = false)
    private int year;

    @Column(name = "grp_commercial", nullable = false)
    private boolean commercial;

    @Column(name = "grp_extramural", nullable = false)
    private boolean extramural;

    @Column(name = "grp_active", nullable = false)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "grp_spccode", nullable = false)
    private Speciality speciality;

    @Transient
    private int specialityCode;

    @ManyToOne
    @JoinColumn(name = "grp_plncode")
    private StudyPlan plan;

    @Transient
    private int planCode;

    private void updatePlan() {
        if (null != plan) {
            planCode = plan.getId();
        }
    }

    private void updateSpeciality() {
        if (null != speciality) {
            specialityCode = speciality.getId();
        }
    }

    @PostLoad
    private void updateCodes() {
        updatePlan();
        updateSpeciality();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isCommercial() {
        return commercial;
    }

    public void setCommercial(boolean commercial) {
        this.commercial = commercial;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
        updateSpeciality();
    }

    public StudyPlan getPlan() {
        return plan;
    }

    public void setPlan(StudyPlan plan) {
        this.plan = plan;
        updatePlan();
    }

    public boolean isExtramural() {
        return extramural;
    }

    public void setExtramural(boolean extramural) {
        this.extramural = extramural;
    }

    public int getSpecialityCode() {
        return specialityCode;
    }

    public void setSpecialityCode(int specialityCode) {
        this.specialityCode = specialityCode;
    }

    public int getPlanCode() {
        return planCode;
    }

    public void setPlanCode(int planCode) {
        this.planCode = planCode;
    }
}
