package controller;


import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import galekop.be.RC.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Remote;
import model.RemoteObserver;
import persistency.GenericDAO;

public class MainWindowController {
	static final Logger logger = LogManager.getLogger(MainWindowController.class.getName());
	private App app;
	private GenericDAO<Remote> remoteDao = new GenericDAO<Remote>(Remote.class);
	private ObservableList<Remote> remoteData = FXCollections.observableArrayList();
	 @FXML
    private JFXButton ButtonOpenRemoteWindow;

    @FXML
    private JFXButton ButtonOpenManagerWindow;
    
    @FXML
    private JFXButton ButtonRefresh;

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
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerView.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle("Remote Manager");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			//add logging
			e.printStackTrace();
		}
    	this.setListFromDb();
    }

    @FXML
    void OpenRemoteWindow(ActionEvent event) {

    }
    
    @FXML
    void RefreshList(ActionEvent event) {
    	this.setListFromDb();
    }
    
    public void setApp(App app) {
    	this.app = app;
    }
    protected void setListFromDb() {
    	LVRemotesMV.getItems().clear();
    	List<Remote> source = remoteDao.findAll();
    	for(Remote remote : source) {
    		remoteData.add(remote);
    	}
    	LVRemotesMV.setItems(remoteData);
    	logger.info("Items loaded from database: " + source.size());
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
