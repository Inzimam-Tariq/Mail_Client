/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.model;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author Inzimam
 */
public class EmailAccountBean {

//    private String mailAddress;
//    private String password;
    private Properties props;

    private Store store;
    private Session session;
    private int loginState = EmailConstants.LOGIN_STATE_NOT_READY;

//    public String getMailAddress() {
//        return mailAddress;
//    }
    public Properties getProperties() {
        return props;
    }

    public Store getStore() {
        return store;
    }

    public Session getSession() {
        return session;
    }

    public int getLoginState() {
        return loginState;
    }

    public EmailAccountBean(String mailAddress, String password) {
//        this.mailAddress = mailAddress;
//        this.password = password;

        props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
//        props.put("mail.transport.protocol", "smtps");
//        props.put("mail.smtps.host", "smtp.gmail.com");
//        props.put("mail.smtps.auth", "true");
//        props.put("incomingHost", "imap.gmail.com");
//        props.put("outgoingHost", "smtp.gmail.com");

        try {
            this.session = Session.getInstance(props, null);
            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", mailAddress, password);
            System.out.println("Successful Authentication!");
            loginState = EmailConstants.LOGIN_STATE_SUCCEEDED;
        } catch (Exception e) {
            e.printStackTrace();
            loginState = EmailConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
        }

    }

}
