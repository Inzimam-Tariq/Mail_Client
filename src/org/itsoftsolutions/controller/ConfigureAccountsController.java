/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javax.swing.JOptionPane;
import org.itsoftsolutions.controller.helpingClasses.FetchDBData;
import org.itsoftsolutions.model.ConfigureAccountBean;

public class ConfigureAccountsController extends AbstractController implements Initializable {

    public ConfigureAccountsController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @FXML
    private TableView<ConfigureAccountBean> accTable;
    @FXML
    private TableColumn<ConfigureAccountBean, Integer> colId;
    @FXML
    private TableColumn<ConfigureAccountBean, String> colMailId;
    @FXML
    private TableColumn<ConfigureAccountBean, String> colPass;
    @FXML
    private TableColumn<ConfigureAccountBean, String> colDisplayName;

    @FXML
    private AnchorPane accInputPane;
    @FXML
    private JFXTextField mailIdField;
    @FXML
    private JFXPasswordField passField;
    @FXML
    private JFXPasswordField reTypePassField;
    @FXML
    private HBox displayNameBox;
    @FXML
    private JFXTextField displayNameField;

    @FXML
    private JFXButton newAccBtn;
    @FXML
    private JFXButton saveAccBtn;
    @FXML
    private JFXButton delAccBtn;

    FetchDBData fetchDBData;
    final String MAIL_ACC_TABLENAME = "MAIL_ACCOUNTS";
    private ObservableList<ConfigureAccountBean> data = FXCollections.observableArrayList();
//            = FXCollections.observableArrayList(
//                    new ConfigureAccountBean(count++, "inzi769@gmail.com", "12233445", "inzi769"),
//                    new ConfigureAccountBean(count++, "inzi769@outlook.com", "12233445", "name"),
//                    new ConfigureAccountBean(count++, "inzi769@yahoo.com", "12233445", "display"),
//                    new ConfigureAccountBean(count++, "inzi769@mailgun.com", "12233445", "mic")
//            );

    @FXML
    void newAccBtn(ActionEvent event) {
        mailIdField.setText("");
        passField.setText("");
        reTypePassField.setText("");
        displayNameField.setText("");
    }

    @FXML
    @SuppressWarnings("unchecked")
    void saveAccAction(ActionEvent event) {
        int rowCount = fetchDBData.getRowCount(MAIL_ACC_TABLENAME);
        String mail = mailIdField.getText().toLowerCase().trim();
        String pass = passField.getText().trim();
        String rePass = reTypePassField.getText().trim();
        String name = displayNameField.getText().trim();
        if (!mail.isEmpty() && !pass.isEmpty() && !rePass.isEmpty()) {
            boolean isConfigAccSuccess
                    = fetchDBData.configureMailAccount(mail, pass, name);
            data.add(new ConfigureAccountBean(rowCount + 1, mail, pass, name));

            if (isConfigAccSuccess) {
                JOptionPane.showMessageDialog(null, "mail configure successfull!!");
            }else{
                JOptionPane.showMessageDialog(null, "unable to configure mail account");
            }

        }
    }

    @FXML
    void delAccAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colMailId.setCellValueFactory(
                new PropertyValueFactory<>("mailAccId")
        );
        colPass.setCellValueFactory(
                new PropertyValueFactory<>("pass")
        );
        colDisplayName.setCellValueFactory(
                new PropertyValueFactory<>("dispName")
        );
        fetchDBData = new FetchDBData();
        int rowCount = fetchDBData.getRowCount(MAIL_ACC_TABLENAME);
        if (rowCount > 0) {
            ResultSet rs = fetchDBData.getResultSet(MAIL_ACC_TABLENAME);
            rowCount = 0;
            try {
                while (rs.next()) {
                    rowCount++;
                    data.add(new ConfigureAccountBean(rowCount,
                            rs.getString("acc_mail"),
                            rs.getString("acc_pass"),
                            rs.getString("acc_disp_name"))
                    );
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Table OR Column Reference");
            }
        }
        accTable.setItems(data);

    }

}
