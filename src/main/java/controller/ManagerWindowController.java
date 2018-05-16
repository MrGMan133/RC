package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton.ButtonType;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Remote;
import model.UserManager;
import persistency.GenericDAO;

public class ManagerWindowController {
	private App app;
	private UserManager userManager = new UserManager();
	private QuestionDialogController deleteMessageController;
	private MainWindowController mainWindowController;
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
    	if (TFNewFrequency.getText().isEmpty() || TFNewFrequency.getText().length() == 0) {
    		TFNewFrequency.setPromptText("Please enter a value");
    		return false;
		}else {
			return true;
		}
    }
    private boolean showQuestionDialog(Remote remote) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionDialog.fxml"));
			Parent root = (Parent) loader.load();
			QuestionDialogController controller = loader.getController();
			controller.setRemote(remote);
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.showAndWait();
			return controller.isOkClicked();
    	} catch (IOException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    @FXML
    void ActivateRemote(ActionEvent event) {
    	Remote selectedRemote = LVAllRemotes.getSelectionModel().getSelectedItem();
    	if (selectedRemote != null) {
			boolean okClicked = this.showQuestionDialog(selectedRemote);
			if (okClicked) {
				if (!selectedRemote.isActive()) {
					userManager.addRemote(selectedRemote);
					remoteDao.update(selectedRemote);
				}
			} 
		} else {
            // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Person Selected");
	        alert.setContentText("Please select a person in the table.");
	        alert.showAndWait();
    	}
    }

    @FXML
    void AddNewRemote(ActionEvent event) {
    	Remote remote = new Remote();
    	this.getRemoteData().add(remote);
    	remoteDao.create(remote);
    }

    @FXML
    void DeactivateRemote(ActionEvent event) {
    	Remote selectedRemote = LVAllRemotes.getSelectionModel().getSelectedItem();
    	if (selectedRemote != null) {
			boolean okClicked = this.showQuestionDialog(selectedRemote);
			if (okClicked) {
				if (selectedRemote.isActive()) {
					userManager.removeRemote(selectedRemote);
					remoteDao.update(selectedRemote);
				}
			} 
		} else {
            // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Person Selected");
	        alert.setContentText("Please select a person in the table.");
	        alert.showAndWait();
    	}
    }

    @FXML
    void RemoveSelectedRemote(ActionEvent event) {
    	Remote selectedRemote = LVAllRemotes.getSelectionModel().getSelectedItem();
    	if(selectedRemote != null) {
    		boolean okClicked = this.showQuestionDialog(selectedRemote);
        	if (okClicked) {
        		remoteDao.delete(selectedRemote);
        		remoteData.remove(selectedRemote);
			}
    	}else {
            // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Person Selected");
	        alert.setContentText("Please select a person in the table.");
	        alert.showAndWait();
    	}
    }

    @FXML
    void UpdateFrequency(ActionEvent event) {
    	if (this.isTextValid()) {
			try {
				userManager.updateFrequency(Double.parseDouble(TFNewFrequency.getText()));
			} catch (Exception e) {
				TFNewFrequency.setPromptText("Please enter a correct value");
			}
		}
    }

}
