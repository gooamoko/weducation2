package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static ru.gooamoko.weducation.utils.Utils.getBooleanString;
import static ru.gooamoko.weducation.utils.Utils.getDateString;

/**
 * Класс для хранения информации о персоне.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "persons")
public class Person implements Serializable {

    @Id
    @Column(name = "psn_pcode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "psn_firstname", nullable = false, length = 50)
    private String firstName;

    @Column(name = "psn_middlename", nullable = false, length = 50)
    private String middleName;

    @Column(name = "psn_lastname", nullable = false, length = 50)
    private String lastName;

    @Column(name = "psn_male", nullable = false)
    private boolean male;

    @Column(name = "psn_foreign", nullable = false)
    private boolean foreign;

    @Column(name = "psn_birthdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "psn_birthplace", nullable = false, length = 255)
    private String birthPlace;

    @Column(name = "psn_orphan", nullable = false)
    private boolean orphan;

    @Column(name = "psn_invalid", nullable = false)
    private boolean invalid;

    @Column(name = "psn_avgball", nullable = false)
    private double averageBall;

    @Column(name = "psn_passportseria", length = 6)
    private String passportSeria;

    @Column(name = "psn_passportnumber", length = 10)
    private String passportNumber;

    @Column(name = "psn_passportdate")
    @Temporal(TemporalType.DATE)
    private Date passportDate;

    @Column(name = "psn_passportdept", length = 255)
    private String passportDept;

    @Column(name = "psn_inn", length = 12)
    private String inn;

    @Column(name = "psn_snils", length = 15)
    private String snils;

    @Column(name = "psn_phones", length = 128)
    private String phones;

    @Column(name = "psn_address", length = 255)
    private String address;

    @Column(name = "psn_lngcode", nullable = false)
    private ForeignLanguage language;

    @ManyToOne
    @JoinColumn(name = "psn_plccode")
    private Place place;

    @Transient
    private int placeCode;

    @OneToMany(mappedBy = "person")
    private List<Delegate> delegates;

    @PostLoad()
    private void updateCode() {
        if (null != place) {
            placeCode = place.getId();
        }
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public String getShortName() {
        try {
            return firstName + " " + middleName.charAt(0) + ". " + lastName.charAt(0) + ".";
        } catch (Exception e) {
            return firstName;
        }
    }

    public String getGender() {
        return male ? "мужской" : "женский";
    }

    public String getForeignString() {
        return getBooleanString(foreign);
    }

    public String getOrphanString() {
        return getBooleanString(orphan);
    }

    public String getInvalidString() {
        return getBooleanString(invalid);
    }

    public String getBirthDateString() {
        return getDateString(birthDate);
    }

    public String getPassportDateString() {
        return getDateString(passportDate);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isForeign() {
        return foreign;
    }

    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public List<Delegate> getDelegates() {
        return delegates;
    }

    public boolean isOrphan() {
        return orphan;
    }

    public void setOrphan(boolean orphan) {
        this.orphan = orphan;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public String getPassportSeria() {
        return passportSeria;
    }

    public void setPassportSeria(String passportSeria) {
        this.passportSeria = passportSeria;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(Date passportDate) {
        this.passportDate = passportDate;
    }

    public String getPassportDept() {
        return passportDept;
    }

    public void setPassportDept(String passportDept) {
        this.passportDept = passportDept;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
        updateCode();
    }

    public int getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(int placeCode) {
        this.placeCode = placeCode;
    }

    public ForeignLanguage getLanguage() {
        return language;
    }

    public void setLanguage(ForeignLanguage language) {
        this.language = language;
    }

    public double getAverageBall() {
        return averageBall;
    }

    public void setAverageBall(double averageBall) {
        this.averageBall = averageBall;
    }
}
