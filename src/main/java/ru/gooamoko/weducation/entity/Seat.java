package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс-сущность для представления количества мест планируемого набора
 * на определенный год по определенной специальности.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "seats")
public class Seat implements Serializable {

    @Id
    @Column(name = "sea_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sea_spccode", nullable = false)
    private Speciality speciality;

    @Column(name = "sea_year", nullable = false)
    private int year;

    @Column(name = "sea_bcount", nullable = false)
    private int budgetCount;

    @Column(name = "sea_ccount", nullable = false)
    private int commercialCount;

    @Column(name = "sea_extramural", nullable = false)
    private boolean extramural;

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBudgetCount() {
        return budgetCount;
    }

    public void setBudgetCount(int budgetCount) {
        this.budgetCount = budgetCount;
    }

    public int getCommercialCount() {
        return commercialCount;
    }

    public void setCommercialCount(int commercialCount) {
        this.commercialCount = commercialCount;
    }

    public boolean isExtramural() {
        return extramural;
    }

    public void setExtramural(boolean extramural) {
        this.extramural = extramural;
    }

    public int getId() {
        return id;
    }
}
