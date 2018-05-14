package controller;


import java.util.List;

import org.omg.CORBA.INITIALIZE;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import galekop.be.RC.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Remote;
import model.RemoteObserver;
import persistency.GenericDAO;

public class MainWindowController {
	private App app;
	private GenericDAO<Remote> remoteDao = new GenericDAO<Remote>(Remote.class);
	private ObservableList<Remote> remoteData = FXCollections.observableArrayList();
	 @FXML
    private JFXButton ButtonOpenRemoteWindow;

    @FXML
    private JFXButton ButtonOpenManagerWindow;

    @FXML
    private JFXListView<Remote> LVRemotesMV;

    @FXML
    private Label LabelRemoteID;

    @FXML
    private Label LabelRemoteFrequency;

    @FXML
    private Label LabelRemoteIsActive;
    
    public void initialize() {
    	this.setListFromDb();
    	LVRemotesMV.getSelectionModel().selectedItemProperty().addListener(
    			new ChangeListener<Remote>() {
    				public void changed(ObservableValue<? extends Remote> ov,
    						Remote oldValue, Remote newValue) {
    					showRemoteDetails(newValue);
    				}
    			});
    }

    @FXML
    void OpenManagerWindow(ActionEvent event) {

    }

    @FXML
    void OpenRemoteWindow(ActionEvent event) {

    }
    public void setApp(App app) {
    	this.app = app;
    }
    private void setListFromDb() {
    	LVRemotesMV.getItems().clear();
    	List<Remote> source = remoteDao.findAll();
    	for(Remote remote : source) {
    		remoteData.add(remote);
    	}
    	LVRemotesMV.setItems(remoteData);
    }
    private void showRemoteDetails(Remote remote) {
    	
    	if (remote != null) {
			LabelRemoteID.setText(Long.toString(remote.getId()));
			LabelRemoteFrequency.setText(Double.toString(remote.getFrequency()));
			LabelRemoteIsActive.setText(Boolean.toString(remote.isActive()));
		}
    	else {
    		LabelRemoteID.setText("");
			LabelRemoteFrequency.setText("");
			LabelRemoteIsActive.setText("");
    	}
    }
}
