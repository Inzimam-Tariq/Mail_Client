/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JOptionPane;
import org.itsoftsolutions.controller.table.AbstractTableItem;

/**
 *
 * @author Inzimam
 */
public class EmailMessageBean extends AbstractTableItem {

    public static HashMap<String, Integer> formattedSize = new HashMap<>();
    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty size;
    private Message msgRef;
    // Attachments handling 
    private List<MimeBodyPart> attachmentList = new ArrayList<>();
    private StringBuffer attachmentNames = new StringBuffer();

    public EmailMessageBean(String subject, String sender, int size, boolean isRead, Message msgRef) {
        super(isRead);
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.size = new SimpleStringProperty(formatSize(size));
        this.msgRef = msgRef;
    }

    private String formatSize(int size) {
        StringBuilder returnVal = new StringBuilder();
        int unit = 1024;
        if (size <= 0) {
            returnVal.append("0");
        } else if (size < unit) {
            returnVal.append(size).append("B");
        } else if (size < (Math.pow(unit, 2))) {
            returnVal.append(size / unit).append("KB");
        } else {
            returnVal.append(size / (int) Math.pow(unit, 2)).append("MB");
        }
        formattedSize.put(returnVal.toString(), size);
        return returnVal.toString();
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
    public String getSize() {
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
    public void clearAttachments(){
        attachmentList.clear();
        attachmentNames.setLength(0);
    }
}
