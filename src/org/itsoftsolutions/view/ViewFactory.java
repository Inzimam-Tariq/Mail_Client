/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.itsoftsolutions.controller.AbstractController;
import org.itsoftsolutions.controller.ComposeWindowController;
import org.itsoftsolutions.controller.EmailDetailController;
import org.itsoftsolutions.controller.MainWindowController;
import org.itsoftsolutions.controller.ModelAccess;

/**
 *
 * @author Inzimam
 */
public class ViewFactory {

    public static ViewFactory defaultFactory = new ViewFactory();
    private static boolean mainViewInitialized = false;

    private String OUR_STYLESHEET = "style.css";
    private String EMAIL_DETAIL_FXML = "emailDetailLayout.fxml";
    private String MAIN_WINDOW_FXML = "mainWindowOptimized.fxml";
    private String COMPOSE_WINDOW_FXML = "composeWindow.fxml";

    private ModelAccess modelAccess = new ModelAccess();
    private MainWindowController mainWindowController;
    private EmailDetailController emailDetailController;

    public Scene getMainWindowScene(){
        if (!mainViewInitialized) {
            mainWindowController = new MainWindowController(modelAccess);
            mainViewInitialized = true;
        }
        return initializeScene(MAIN_WINDOW_FXML, mainWindowController);
    }

    public Scene getEmailDetailScene() {
        emailDetailController = new EmailDetailController(modelAccess);

        return initializeScene(EMAIL_DETAIL_FXML, emailDetailController);
    }
    
    public Scene getComposeWindowScene(){
        AbstractController composeController = new ComposeWindowController(modelAccess);
        return initializeScene(COMPOSE_WINDOW_FXML, composeController);
    }

    private Scene initializeScene(String fxmlPath, AbstractController controller) {
        FXMLLoader fXMLLoader;
        Parent parent;
        Scene scene;
        try {
            fXMLLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            fXMLLoader.setController(controller);
            parent = fXMLLoader.load();
        } catch (Exception e) {
            return null;
        }
        scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource(OUR_STYLESHEET).toExternalForm());
        return scene;
    }

    public Node loadIcons(String treeItemValue) {
        String treeItemValueLowerCase = treeItemValue.toLowerCase();
        ImageView returnIcon = null;
        try {
            if (treeItemValueLowerCase.contains("inbox")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/mail.png")));
            } else if (treeItemValueLowerCase.contains("sent")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/sent.png")));
            } else if (treeItemValueLowerCase.contains("draft")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/draft.png")));
            } else if (treeItemValueLowerCase.contains("spam")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/folder.png")));
            } else if (treeItemValueLowerCase.contains("trash")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/trash.png")));
            } else if (treeItemValueLowerCase.contains("star")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/folder_starred.png")));
            } else if (treeItemValueLowerCase.contains("@")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/email.png")));
            } else {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/folder.png")));
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Images Folder Not Found!");
        }
        returnIcon.setFitHeight(16);
        returnIcon.setFitWidth(16);
        return returnIcon;
    }

}
