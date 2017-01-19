/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.model.folder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import org.itsoftsolutions.model.EmailMessageBean;
import org.itsoftsolutions.view.ViewFactory;

/**
 *
 * @author Inzimam
 */
public class EmailFolderBean<T> extends TreeItem<String> {

    private boolean topElement = false;
    private int unreadMsgCount;
    private String name;
    private String completeName;
    private ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();

    /**
     * Constructor for topElements
     *
     * @param value
     */
    public EmailFolderBean(String value) {
        super(value, ViewFactory.defaultFactory.loadIcons(value));
        this.name = value;
        this.completeName = value;
        this.data = null;
        this.topElement = true;
        this.setExpanded(true);
    }

    public EmailFolderBean(String value, String completeName) {
        super(value, ViewFactory.defaultFactory.loadIcons(value));
        this.name = value;
        this.completeName = completeName;
    }

    private void updateValue() {
        if (unreadMsgCount > 0) {
            this.setValue(name + "   (" + unreadMsgCount + ")");
        } else {
            this.setValue(name);
        }
    }

    public void incrementUnreadMsgs(int newMsg) {
        unreadMsgCount += newMsg;
        updateValue();
    }

    public void decrementUnreadMsgs(int newMsg) {
        unreadMsgCount -= newMsg;
        updateValue();
    }

    public void addEmail(int position, Message msg) {
        try {
            boolean isRead = msg.getFlags().contains(Flags.Flag.SEEN);
            EmailMessageBean emailMessageBean = new EmailMessageBean(msg.getSubject(),
                    msg.getFrom()[0].toString(),
                    msg.getSize(),
                    isRead,
                    msg);
            if (position < 0) {
                getData().add(emailMessageBean);
            } else {
                getData().add(position, emailMessageBean);
            }
            if (!isRead) {
                incrementUnreadMsgs(1);
            }
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, "Unable to Fetch Email Message");
        }
    }

    public boolean isTopElement() {
        return topElement;
    }

    public ObservableList<EmailMessageBean> getData() {
        return data;
    }
}
