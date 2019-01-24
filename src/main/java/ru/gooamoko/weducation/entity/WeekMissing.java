package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "weekmissings")
public class WeekMissing implements Serializable {

    @Id
    @Column(name = "wms_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "wms_psncode", nullable = false)
    private Person person;

    @Column(name = "wms_month", nullable = false)
    private int month;

    @Column(name = "wms_year", nullable = false)
    private int year;

    @Column(name = "wms_week", nullable = false)
    private int week;

    @Column(name = "wms_legal", nullable = false)
    private int legal;

    @Column(name = "wms_illegal", nullable = false)
    private int illegal;

    @ManyToOne
    @JoinColumn(name = "wms_crdcode", nullable = false)
    private StudyCard card;

    @Transient
    private int cardCode;

    public int getId() {
        return id;
    }

    public int getAll() {
        return legal + illegal;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getLegal() {
        return legal;
    }

    public void setLegal(int legal) {
        this.legal = legal;
    }

    public int getIllegal() {
        return illegal;
    }

    public void setIllegal(int illegal) {
        this.illegal = illegal;
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
