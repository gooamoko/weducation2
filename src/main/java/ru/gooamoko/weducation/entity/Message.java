package ru.gooamoko.weducation.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity-класс для сопоставления с таблицей сообщений
 *
 * @author Voronin Leonid
 * @since 08.05.2016
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_pcode")
    private int id;

    @Column(name = "msg_from", nullable = false)
    private String from;

    @ManyToOne
    @JoinColumn(name = "msg_srcaccount")
    private Account sourceAccount; // NULL если послано информационной системой

    @ManyToOne
    @JoinColumn(name = "msg_dstaccount", nullable = false)
    private Account destinationAccount;

    @Column(name = "msg_title", nullable = false)
    private String title;

    @Column(name = "msg_text", nullable = false, length = 65535)
    private String text;

    @Column(name = "msg_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(name = "msg_received", nullable = false)
    private boolean received;

    @Column(name = "msg_deleted", nullable = false)
    private boolean deleted;

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
