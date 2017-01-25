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
public class EmailConstants {
    
    public static final int LOGIN_STATE_NOT_READY = 0;
    public static final int LOGIN_STATE_FAILED_BY_NETWORK = 1;
    public static final int LOGIN_STATE_FAILED_BY_CREDENTIALS = 2;
    public static final int LOGIN_STATE_SUCCEEDED = 3;
    
    public static final int MESSAGE_SEND_OK = 5;
    public static final int MESSAGE_SEND_ERROR = 6;
}
