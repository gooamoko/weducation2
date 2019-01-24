package ru.gooamoko.weducation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Table(name = "cmarks")
public class CourseWorkMark implements Serializable {

    @Id
    @Column(name = "cmk_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cmk_mark", nullable = false)
    private int mark;

    @Column(name = "cmk_theme", length = 255)
    private String theme;

    @Column(name = "cmk_course", nullable = false)
    private int course;

    @Column(name = "cmk_semester", nullable = false)
    private int semester;

    @ManyToOne
    @JoinColumn(name = "cmk_crdcode", nullable = false)
    private StudyCard card;

    @ManyToOne
    @JoinColumn(name = "cmk_subcode", nullable = false)
    private Subject subject;

    @Transient
    private int subjectCode;

    private void updateSubjectCode() {
        if (null != subject) {
            subjectCode = subject.getId();
        }
    }

    @PostLoad
    private void updateCodes() {
        updateSubjectCode();
    }

    public int getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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
        updateSubjectCode();
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
