package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ManagerWindowController {
	@FXML
    private AnchorPane ActivateRemote;

    @FXML
    private JFXListView<?> LVAllRemotes;

    @FXML
    private JFXButton ButtonActivateRemote;

    @FXML
    private JFXButton ButtonDeactivateRemote;

    @FXML
    private JFXButton ButtonAddNewRemote;

    @FXML
    private JFXButton ButtonRemoveSelectedRemote;

    @FXML
    private JFXTextField TFNewFrequency;

    @FXML
    private JFXButton ButtonUpdateFrequency;

    @FXML
    void ActivateRemote(ActionEvent event) {

    }

    @FXML
    void AddNewRemote(ActionEvent event) {

    }

    @FXML
    void DeactivateRemote(ActionEvent event) {

    }

    @FXML
    void RemoveSelectedRemote(ActionEvent event) {

    }

    @FXML
    void UpdateFrequency(ActionEvent event) {

    }

}
