package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс для хранения переименования учебного заведения.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "renamings")
public class Renaming implements Serializable {

    @Id
    @Column(name = "ren_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ren_oldname", nullable = false, length = 255)
    private String oldName;

    @Column(name = "ren_newname", nullable = false, length = 255)
    private String newName;

    @Column(name = "ren_date", columnDefinition = "date NOT NULL")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    public int getId() {
        return id;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }
}
