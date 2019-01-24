package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fmarks")
public class FinalMark implements Serializable {

    @Id
    @Column(name = "fmk_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fmk_audload", nullable = false)
    private int auditoryLoad;

    @Column(name = "fmk_maxload", nullable = false)
    private int maximumLoad;

    @Column(name = "fmk_mark", nullable = false)
    private int mark;

    @ManyToOne
    @JoinColumn(name = "fmk_crdcode", nullable = false)
    private StudyCard card;

    @ManyToOne
    @JoinColumn(name = "fmk_subcode")
    private Subject subject;

    @Transient
    private int subjectCode;

    @ManyToOne
    @JoinColumn(name = "fmk_modcode")
    private StudyModule module;

    @Transient
    private int moduleCode;

    private void updateModuleCode() {
        if (null != module) {
            moduleCode = module.getId();
        }
    }

    private void updateSubjectCode() {
        if (null != subject) {
            subjectCode = subject.getId();
        }
    }

    @PostLoad
    private void updateCodes() {
        updateModuleCode();
        updateSubjectCode();
    }

    public boolean isModuleMark() {
        return (null != module) && (null == subject);
    }

    public int getId() {
        return id;
    }

    public int getAuditoryLoad() {
        return auditoryLoad;
    }

    public void setAuditoryLoad(int auditoryLoad) {
        this.auditoryLoad = auditoryLoad;
    }

    public int getMaximumLoad() {
        return maximumLoad;
    }

    public void setMaximumLoad(int maximumLoad) {
        this.maximumLoad = maximumLoad;
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

    public StudyModule getModule() {
        return module;
    }

    public void setModule(StudyModule module) {
        this.module = module;
        updateModuleCode();
    }

    public int getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(int moduleCode) {
        this.moduleCode = moduleCode;
    }
}
