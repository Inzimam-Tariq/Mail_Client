/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.itsoftsolutions.model.EmailAccountBean;
import org.itsoftsolutions.model.EmailMessageBean;

/**
 *
 * @author Inzimam
 */
public class Test {

    public static void main(String[] args) {
        
        final EmailAccountBean accountBean = new EmailAccountBean("inzi769@gmail.com",
                "769inzimam-9771");
        ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
        
    }
}
