package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс специальности.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "specialities")
public class Speciality implements Serializable {

    @Id
    @Column(name = "spc_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "spc_name", nullable = false, length = 10)
    private String name;

    @Column(name = "spc_description", nullable = false, length = 255)
    private String description;

    @Column(name = "spc_actual", nullable = false)
    private boolean actual;

    @Column(name = "spc_aviable", nullable = false)
    private boolean aviable;

    public int getId() {
        return id;
    }

    public String getNameForList() {
        return name;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public boolean isAviable() {
        return aviable;
    }

    public void setAviable(boolean aviable) {
        this.aviable = aviable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}