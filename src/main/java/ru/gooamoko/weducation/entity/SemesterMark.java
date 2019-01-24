package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс оценок за семестр
 */
@Entity
@Table(name = "semestermarks")
public class SemesterMark implements Serializable {

    @Id
    @Column(name = "smk_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "smk_semester", nullable = false)
    private int semester;

    @Column(name = "smk_course", nullable = false)
    private int course;

    @Column(name = "smk_mark", nullable = false)
    private int mark;

    @ManyToOne
    @JoinColumn(name = "smk_psncode", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "smk_crdcode", nullable = false)
    private StudyCard card;

    @ManyToOne
    @JoinColumn(name = "smk_subcode")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "smk_modcode")
    private StudyModule module;

    public int getId() {
        return id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public StudyCard getCard() {
        return card;
    }

    public void setCard(StudyCard card) {
        this.card = card;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public StudyModule getModule() {
        return module;
    }

    public void setModule(StudyModule module) {
        this.module = module;
    }
}
