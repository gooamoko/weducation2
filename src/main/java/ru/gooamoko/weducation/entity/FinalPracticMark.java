package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fpmarks")
public class FinalPracticMark implements Serializable {

    @Id
    @Column(name = "fpm_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fpm_mark", nullable = false)
    private int mark;

    @ManyToOne
    @JoinColumn(name = "fpm_crdcode", nullable = false)
    private StudyCard card;

    @ManyToOne
    @JoinColumn(name = "fpm_fpccode", nullable = false)
    private FinalPractic practic;

    @Transient
    private int practicCode;

    private void updatePracticCode() {
        if (practic != null) {
            practicCode = practic.getId();
        }
    }

    @PostLoad
    private void updateCode() {
        updatePracticCode();
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

    public StudyCard getCard() {
        return card;
    }

    public void setCard(StudyCard card) {
        this.card = card;
    }

    public FinalPractic getPractic() {
        return practic;
    }

    public void setPractic(FinalPractic practic) {
        this.practic = practic;
        updatePracticCode();
    }

    public int getPracticCode() {
        return practicCode;
    }

    public void setPracticCode(int practicCode) {
        this.practicCode = practicCode;
    }
}
