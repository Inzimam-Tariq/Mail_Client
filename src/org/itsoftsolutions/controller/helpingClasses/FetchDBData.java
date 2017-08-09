/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.helpingClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 *
 * @author Inzimam
 */
public class FetchDBData {

    private final String MAIL_ACC_TABLENAME = "MAIL_ACCOUNTS";
    private final String LOGIN_TABLENAME = "LOGIN_TABLE";

    private String dbPath = "jdbc:derby://localhost:1527/mail_client_db";
    private Statement st;
    private ResultSet res;
    private Connection con;
    
    private ShowDialog dialog;

    public FetchDBData() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            dialog.show(Alert.AlertType.ERROR,
                    "Error",
                    "ClassNotFoundException",
                    "Derby driver not found!");
        }
        try {
            con = DriverManager.getConnection(dbPath,
                    "Inzimam", "769inzi");
            st = con.createStatement();
        } catch (SQLException ex) {
            dialog.show(Alert.AlertType.ERROR,
                    "Error",
                    "Exception Connecting to DB",
                    "Your DB credentials are not correct!");
        }
    }
//    Getters for global fields

    public String getMailAccTableName() {
        return MAIL_ACC_TABLENAME;
    }

    public String getLoginTableName() {
        return LOGIN_TABLENAME;
    }

    public ResultSet getResultSet(String tableName) {
        try {
            res = st.executeQuery("SELECT * FROM Inzimam." + tableName);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Table not found");
            res = null;
        }
        return res;
    }

    public ResultSet getResultSet(String tableName, String whereCon) {
        try {
            res = st.executeQuery("SELECT * FROM Inzimam." + tableName + " WHERE " + whereCon);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Table not found\n" + ex);
            res = null;
        }
        return res;
    }

    public int getRowCount(String tableName) {
        int count = 0;
        try {
            res = st.executeQuery("SELECT COUNT(*) FROM " + tableName);
            while (res.next()) {
                count = res.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Table not found record getting");
        }
        return count;
    }

    public int getLastId(String tableName, String colName) {
        int lastId = 0;
        try {
            res = st.executeQuery("SELECT MAX(" + colName + ") from " + tableName);
            while (res.next()) {
                lastId = res.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Table not found\nget last id");
        }
        return lastId;
    }

    public boolean StoreSignUpInfo(String uname, String upass) {
        try {
            st.execute("insert into " + getLoginTableName() + " (uname, upass) "
                    + "values('" + uname + "', '" + upass + "')");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean configureMailAccount(String mail, String pass, String dispName) {
        try {
            st.execute("insert into mail_accounts (acc_mail, acc_pass, acc_disp_name) "
                    + "values('" + mail + "', '" + pass + "', '" + dispName + "')");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
