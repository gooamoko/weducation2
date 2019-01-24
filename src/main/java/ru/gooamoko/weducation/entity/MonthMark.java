package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "monthmarks")
public class MonthMark implements Serializable {

    @Id
    @Column(name = "mmk_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mmk_month", nullable = false)
    private int month;

    @Column(name = "mmk_year", nullable = false)
    private int year;

    @Column(name = "mmk_mark", nullable = false)
    private int mark;

    @ManyToOne
    @JoinColumn(name = "mmk_subcode")
    private Subject subject;

    @Transient
    private int subjectCode;

    @ManyToOne
    @JoinColumn(name = "mmk_psncode")
    private Person person;

    @Transient
    private int personCode;

    @ManyToOne
    @JoinColumn(name = "mmk_crdcode")
    private StudyCard card;

    @Transient
    private int cardCode;

    @PostLoad
    private void updateCodes() {
        subjectCode = subject.getId();
        personCode = person.getId();
        cardCode = card.getId();
    }

    public int getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getPersonCode() {
        return personCode;
    }

    public void setPersonCode(int personCode) {
        this.personCode = personCode;
    }

    public StudyCard getCard() {
        return card;
    }

    public void setCard(StudyCard card) {
        this.card = card;
    }

    public int getCardCode() {
        return cardCode;
    }

    public void setCardCode(int cardCode) {
        this.cardCode = cardCode;
    }
}
