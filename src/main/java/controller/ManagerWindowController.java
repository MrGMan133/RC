package controller;

import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import galekop.be.RC.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Remote;
import model.UserManager;
import persistency.GenericDAO;

public class ManagerWindowController {
	private App app;
	private UserManager userManager;
	private GenericDAO<Remote> remoteDao = new GenericDAO<Remote>(Remote.class);
	private ObservableList<Remote> remoteData = FXCollections.observableArrayList();
	@FXML
    private AnchorPane ActivateRemote;

    @FXML
    private JFXListView<Remote> LVAllRemotes;

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
    
    public void initialize() {
    	this.setListFromDb();
    }

    private void setListFromDb() {
    	LVAllRemotes.getItems().clear();
    	List<Remote> source = remoteDao.findAll();
    	for(Remote remote : source) {
    		remoteData.add(remote);
    	}
    	LVAllRemotes.setItems(remoteData);
    }
    private ObservableList<Remote> getRemoteData(){
    	return remoteData;
    }
    private boolean isTextValid() {
    	if (TFNewFrequency.getText().isEmpty()) {
			
		}
    }
    @FXML
    void ActivateRemote(ActionEvent event) {

    }

    @FXML
    void AddNewRemote(ActionEvent event) {
    	Remote remote = new Remote();
    	this.getRemoteData().add(remote);
    	remoteDao.create(remote);
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
