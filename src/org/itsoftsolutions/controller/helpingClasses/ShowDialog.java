/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.helpingClasses;

import javafx.scene.control.Alert;

/**
 *
 * @author Inzimam
 */
public class ShowDialog {

    public Alert show(Alert.AlertType alertType, String title, String headerText, String msg) {
        Alert al = new Alert(alertType);
        al.setTitle(title);
        al.setHeaderText(headerText);
        al.setContentText(msg);
        al.showAndWait();

        return al;
    }

}
