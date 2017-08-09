/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.helpingClasses;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Inzimam
 */
public class SystemNotificationClass {

    public Notifications showSystemNotification(String title, String msg, int duration) {
        Notifications notifications = Notifications.create()
                .title(title)
                .text(msg)
                .hideAfter(Duration.seconds(duration))
                .position(Pos.BASELINE_RIGHT)
                .onAction((ActionEvent event1) -> {

                });
//                    notifications.show();
        return notifications;
    }

}
