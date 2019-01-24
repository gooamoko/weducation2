package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс законного представителя.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "delegates")
public class Delegate implements Serializable {

    @Id
    @Column(name = "dlg_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dlg_fullname", nullable = false, length = 128)
    private String fullName;

    @Column(name = "dlg_phones", length = 128)
    private String phones;

    @Column(name = "dlg_job", nullable = false, length = 255)
    private String job;

    @Column(name = "dlg_post", length = 255)
    private String post;

    @Column(name = "dlg_description", length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "dlg_psncode", nullable = false)
    private Person person;

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
