package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс для итоговой практики, которая пойдет в выписку.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "finalpractics")
public class FinalPractic implements Serializable {

    @Id
    @Column(name = "fpc_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fpc_name", nullable = false, length = 255)
    private String name;

    @Column(name = "fpc_length", nullable = false)
    private float length;

    @Column(name = "fpc_number", nullable = false)
    private int number;

    @ManyToOne
    @JoinColumn(name = "fpc_plncode", nullable = false)
    private StudyPlan plan;

    @Transient
    private int planCode;

    @PostLoad
    private void updateCode() {
        if (null != plan) {
            planCode = plan.getId();
        }
    }

    public FinalPractic() {
        // Конструктор по умолчанию без параметров
    }

    public FinalPractic(final FinalPractic source) {
        this.length = source.getLength();
        this.number = source.getNumber();
        this.name = source.getName();
        this.plan = source.getPlan();
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

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public StudyPlan getPlan() {
        return plan;
    }

    public void setPlan(StudyPlan plan) {
        this.plan = plan;
        updateCode();
    }

    public int getPlanCode() {
        return planCode;
    }

    public void setPlanCode(int planCode) {
        this.planCode = planCode;
    }


}
