package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс оценки за практику по модулям.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "pmarks")
public class PracticMark implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pmk_pcode")
    private int id;

    @Column(name = "pmk_mark", nullable = false)
    private int mark;

    @ManyToOne
    @JoinColumn(name = "pmk_prccode", nullable = false)
    private Practic practic;

    @ManyToOne
    @JoinColumn(name = "pmk_crdcode", nullable = false)
    private StudyCard card;

    public int getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Practic getPractic() {
        return practic;
    }

    public void setPractic(Practic practic) {
        this.practic = practic;
    }

    public StudyCard getCard() {
        return card;
    }

    public void setCard(StudyCard card) {
        this.card = card;
    }
}
