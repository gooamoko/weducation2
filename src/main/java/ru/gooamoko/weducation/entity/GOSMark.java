package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gmarks")
public class GOSMark implements Serializable {

    @Id
    @Column(name = "gmk_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "gmk_mark", nullable = false)
    private int mark;

    @ManyToOne
    @JoinColumn(name = "gmk_subcode", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "gmk_crdcode", nullable = false)
    private StudyCard card;

    @Transient
    private int subjectCode;

    private void updateSubjectCode() {
        if (subject != null) {
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        updateSubjectCode();
    }

    public StudyCard getCard() {
        return card;
    }

    public void setCard(StudyCard card) {
        this.card = card;
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }
}
