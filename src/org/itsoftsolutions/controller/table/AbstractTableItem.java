/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.table;

import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Inzimam
 */
public abstract class AbstractTableItem {

    private SimpleBooleanProperty read = new SimpleBooleanProperty();

    public AbstractTableItem(boolean isRead) {
        this.setRead(isRead);
    }

    public SimpleBooleanProperty getReadProperty() {

        return read;
    }

    public boolean isRead() {
        return read.get();
    }

    public void setRead(boolean isRead) {
        read.set(isRead);
    }
}
