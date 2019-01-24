package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс учебной нагрузки по дисциплине.
 */
@Entity
@Table(name = "load")
public class SubjectLoad implements Serializable {

    @Id
    @Column(name = "lod_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "lod_subcode", nullable = false)
    private Subject subject;

    @Transient
    private int subjectCode;

    @Column(name = "lod_course", nullable = false)
    private int course;

    @Column(name = "lod_semester", nullable = false)
    private int semester;

    @Column(name = "lod_auditory", nullable = false)
    private int auditoryLoad;

    @Column(name = "lod_courseproj", nullable = false)
    private int courseProjectLoad;

    @Column(name = "lod_maximum", nullable = false)
    private int maximumLoad;

    @Column(name = "lod_exfcode")
    private ExamForm examForm = ExamForm.NONE;

    @PostLoad
    private void updateCodes() {
        if (null != subject) {
            subjectCode = subject.getId();
        }
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        if (null != subject) {
            subjectCode = subject.getId();
        } else {
            subjectCode = 0;
        }
    }

    public SubjectLoad() {
        // empty constructor
    }

    public SubjectLoad(final SubjectLoad source) {
        subject = source.getSubject();
        if (null != subject) {
            subjectCode = subject.getId();
        }
        course = source.getCourse();
        semester = source.getSemester();
        auditoryLoad = source.getAuditoryLoad();
        courseProjectLoad = source.getCourseProjectLoad();
        examForm = source.getExamForm();
        maximumLoad = source.getMaximumLoad();
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

    public int getCourseProjectLoad() {
        return courseProjectLoad;
    }

    public void setCourseProjectLoad(int courseProjectLoad) {
        this.courseProjectLoad = courseProjectLoad;
    }

    public ExamForm getExamForm() {
        return examForm;
    }

    public void setExamForm(ExamForm examForm) {
        this.examForm = examForm;
    }
}
