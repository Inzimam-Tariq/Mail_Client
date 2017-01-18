/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.model;

import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
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
    private String msgContent;

    public EmailMessageBean(String subject, String sender, int size, String msgContent, boolean isRead) {
        super(isRead);
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.size = new SimpleStringProperty(formatSize(size));
        this.msgContent = msgContent;
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

    /**
     * @return the msgContent
     */
    public String getMsgContent() {
        return msgContent;
    }

}
