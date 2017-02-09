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
import javax.swing.JOptionPane;

/**
 *
 * @author Inzimam
 */
public class FetchDBData {

    private String dbPath = "jdbc:derby://localhost:1527/mail_client_db";
    private Statement st;
    private ResultSet res;
    private Connection con;

    public FetchDBData() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(null, "Derby driver not found!");
        }
        try {
            con = DriverManager.getConnection(dbPath,
                    "Inzimam", "769inzi");
            st = con.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Your DB credentials are not correct!");
        }
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

    public boolean StoreSignUpInfo(String tableName, String uname, String upass) {
        try {
            st.execute("insert into " + tableName + " (uname, upass) "
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
                    + "values('" + mail + "', '" + pass + "', '"+dispName+"')");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
