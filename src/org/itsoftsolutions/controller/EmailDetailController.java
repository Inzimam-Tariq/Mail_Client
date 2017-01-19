package org.itsoftsolutions.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import org.itsoftsolutions.model.EmailMessageBean;
import org.itsoftsolutions.view.ViewFactory;

/**
 * FXML Controller class
 *
 * @author Inzimam
 */
public class EmailDetailController extends AbstractController implements Initializable {

    public EmailDetailController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @FXML
    private Label senderLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private WebView popupWebView;
    
    @FXML
    void ilegalOpperationAction() throws OperationNotSupportedException {
    	ViewFactory view= new ViewFactory();
    	Scene mainScene = view.getMainWindowScene();
    	Stage stage = new Stage();
    	stage.setScene(mainScene);
    	stage.show();
    	
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmailMessageBean selectedMessage = getModelAccess().getSelectedMessage();
        
        subjectLabel.setText("Subject: " + selectedMessage.getSubject());
        senderLabel.setText("Sender: " + selectedMessage.getSender());
//        popupWebView.getEngine().loadContent(selectedMessage.getMsgContent());

    }

}
