/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Inzimam
 */
public class ConfigureAccountBean {

    private SimpleIntegerProperty id;
    private SimpleStringProperty mailAccId;
    private SimpleStringProperty pass;
    private SimpleStringProperty serverName;    
    private SimpleStringProperty dispName;    

    public ConfigureAccountBean(int id, String mailAccId, String pass, String serverName, String dispName) {
        this.id = new SimpleIntegerProperty(id);
        this.mailAccId = new SimpleStringProperty(mailAccId);
        this.pass = new SimpleStringProperty(pass);
        this.serverName = new SimpleStringProperty(serverName);
        this.dispName = new SimpleStringProperty(dispName);
    }

    public int getId() {
        return id.get();
    }

    public String getMailAccId() {
        return mailAccId.get();
    }

    public String getPass() {
        return pass.get();
    }
    
    public String getServerName() {
        return serverName.get();
    }

    public String getDispName() {
        return dispName.get();
    }
}
