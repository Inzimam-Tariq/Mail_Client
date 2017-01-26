/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JOptionPane;
import org.itsoftsolutions.controller.table.AbstractTableItem;
import org.itsoftsolutions.controller.table.formatedSize;

/**
 *
 * @author Inzimam
 */
public class EmailMessageBean extends AbstractTableItem {

    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleObjectProperty<formatedSize> size;
    private Message msgRef;
    private SimpleObjectProperty<Date> date;
    // Attachments handling 
    private List<MimeBodyPart> attachmentList = new ArrayList<>();
    private StringBuffer attachmentNames = new StringBuffer();

    public EmailMessageBean(String subject, String sender, int size, boolean isRead, Date date, Message msgRef) {
        super(isRead);
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.size = new SimpleObjectProperty<>(new formatedSize(size));
        this.date = new SimpleObjectProperty<>(date);
        this.msgRef = msgRef;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject.get();
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender.get();
    }

    /**
     * @return the size
     */
    public formatedSize getSize() {
        return size.get();
    }

    public Message getMsgRef() {
        return msgRef;
    }

    public List<MimeBodyPart> getAttachmentList() {
        return attachmentList;
    }

    public String getAttachmentNames() {
        return attachmentNames.toString();
    }

    public void addAttachment(MimeBodyPart mbp) {
        attachmentList.add(mbp);
        try {
            attachmentNames.append(mbp.getFileName()).append(";");
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, "Unable to get attachment file name!");
        }
    }

    public boolean hasAttachment() {
        return attachmentList.size() > 0;
    }

    // clear methods:

    public void clearAttachments() {
        attachmentList.clear();
        attachmentNames.setLength(0);
    }

    public Date getDate() {
        return date.get();
    }
}
