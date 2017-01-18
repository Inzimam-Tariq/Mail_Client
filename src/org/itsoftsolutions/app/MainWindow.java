/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.itsoftsolutions.view.ViewFactory;

/**
 *
 * @author Inzimam
 */
public class MainWindow extends Application {

    @Override
    public void start(Stage stage) {

        ViewFactory viewFactory = ViewFactory.defaultFactory;
        stage.setTitle("Mail Client Main");
        Scene scene = viewFactory.getMainWindowScene();

        stage.setScene(scene);
//        stage.setMaximized(true);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
