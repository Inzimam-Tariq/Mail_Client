/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.model;

/**
 *
 * @author Inzimam
 */
public class Singleton {

    private Singleton() {
    }
    private static Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }

    private EmailMessageBean message;

    public EmailMessageBean getMessage() {
        return message;
    }

    public void setMessage(EmailMessageBean message) {
        this.message = message;
    }
}
