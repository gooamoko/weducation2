package ru.gooamoko.weducation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Класс заявки для поступления определенной персоны на определенную
 * специальность по определенной форме обучения.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "requests")
public class Request implements Serializable {

    @Id
    @Column(name = "req_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "req_psncode")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "req_spccode")
    private Speciality speciality;

    @Column(name = "req_year")
    private int year;

    @Column(name = "req_extramural")
    private boolean extramural;

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

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

    public boolean isExtramural() {
        return extramural;
    }

    public void setExtramural(boolean extramural) {
        this.extramural = extramural;
    }
}
