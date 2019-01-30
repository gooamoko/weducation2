package ru.gooamoko.weducation.entity;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
  @NotEmpty
  @Length(max = 10)
  private String name;

  @Column(name = "spc_description", nullable = false)
  @NotEmpty
  @Length(max = 255)
  private String description;

  @Column(name = "spc_actual", nullable = false)
  private boolean actual;

  @Column(name = "spc_aviable", nullable = false)
  private boolean aviable;

  public void updateFrom(Speciality other) {
    actual = other.isActual();
    aviable = other.isAviable();
    name = other.getName();
    description = other.getDescription();
  }

  public int getId() {
    return id;
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