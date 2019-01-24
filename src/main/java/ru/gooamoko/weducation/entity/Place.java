package ru.gooamoko.weducation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Класс, инкапсулирующий в себе населенный пункт.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "places")
public class Place implements Serializable {

    @Id
    @Column(name = "plc_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "plc_type", nullable = false)
    private PlaceType type;

    @Column(name = "plc_name", nullable = false, length = 255)
    private String name;

    public String getFullName() {
        return type.getShortName() + " " + name;
    }

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
