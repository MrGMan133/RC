package controller;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Remote;

public class QuestionDialogController {
	private Remote remoteToAlter;
	private boolean okClicked = false;
    @FXML
    private Label labelRemove;
    
    @FXML
    private JFXButton ButtonOK;

    @FXML
    private JFXButton ButtonCancel;
   
    
    public void setRemote(Remote remote) {
    	this.remoteToAlter = remote;
    	labelRemove.setText("Are you sure");
    }
    public boolean isOkClicked() {
    	return okClicked;
    }
    @FXML
    void CancelDeleteRemote(ActionEvent event) {
    	Stage stage = (Stage) ButtonOK.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void OKDeleteRemote(ActionEvent event) {
    	Stage stage = (Stage) ButtonOK.getScene().getWindow();
    	okClicked = true;
    	stage.close();
    }
}
