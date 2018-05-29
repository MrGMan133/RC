package controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import galekop.be.RC.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Gate;
import model.Remote;
import model.UserManager;
import persistency.GenericDAO;

public class ManagerWindowController {
	private App app;
	private UserManager userManager = new UserManager();
	private Gate gate = new Gate();
	private QuestionDialogController deleteMessageController;
	private List<Remote> sourceIsActive;
	private GenericDAO<Remote> remoteDao = new GenericDAO<Remote>(Remote.class);
	private GenericDAO<UserManager> userManagerDao = new GenericDAO<UserManager>(UserManager.class);
	private GenericDAO<Gate> gateDao = new GenericDAO<Gate>(Gate.class);
	private ObservableList<Remote> remoteData = FXCollections.observableArrayList();
	static final Logger logger = LogManager.getLogger(ManagerWindowController.class.getName());
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
    
    @FXML
    private JFXButton ButtonRequest;
    
    @FXML
    private Label LabelFrequency;
    
    @FXML
    private ToggleButton ToggleGate;
    
    @FXML
    private JFXButton ButtonAdd10Remotes;
    
    public void initialize() {
    	this.setUserManager();
    	this.setGate();
    	this.setListFromDb();
    	LabelFrequency.setText(userManager.toString());
    }
    public void setApp(App app) {
    	this.app = app;
    }
    private void setUserManager() {
    	this.userManager = userManagerDao.findOne(1);
    	logger.info("User Manager: " + this.userManager.toString());
    }
    private void setGate() {
    	this.gate = gateDao.findOne(1);
    	logger.info("Gate: " + this.gate.toString());
    }
    private void setListFromDb() {
    	LVAllRemotes.getItems().clear();
    	sourceIsActive = remoteDao.executeNamedQuery("Remote.findIsActive");
    	for (Remote remote : sourceIsActive) {
    		userManager.addRemoteToList(remote);
		}
    	logger.info("Items loaded from database and added to Active List: " + sourceIsActive.size());
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
					logger.info("Remote: " + selectedRemote.toString() + " activated.");
					this.setListFromDb();
				}
			}
		} else {
            // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No remote Selected");
	        alert.setContentText("Please select a remote in the table.");
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
					logger.info("Remote: " + selectedRemote.toString() + " de-activated.");
				}else {
			        Alert alert = new Alert(AlertType.WARNING);
			        alert.setTitle("Already Deactive");
			        alert.setHeaderText("Already Deactive");
			        alert.setContentText("This remote is already deactivated");
			        alert.showAndWait();
				}
				this.setListFromDb();
			} 
		} else {
            // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No remote Selected");
	        alert.setContentText("Please select a remote in the table.");
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
        		logger.info("Remote: " + selectedRemote.toString() + " removed.");
			}
    	}else {
            // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No remote selected");
	        alert.setContentText("Please select a remote in the table.");
	        alert.showAndWait();
    	}
    	this.setListFromDb();
    }

    @FXML
    void UpdateFrequency(ActionEvent event) {
    	if (this.isTextValid()) {
			try {
				this.setListFromDb();
				Double updateFrequency = Double.parseDouble(TFNewFrequency.getText()); 
				userManager.updateFrequency(updateFrequency);
				//List<Remote> activeList = userManager.getRemotesToUpdate();
				for (Remote remote : sourceIsActive) {
					remoteDao.update(remote);
					logger.info("Update: " + remote.getFrequency());
				}
				gate.updateFrequency(updateFrequency);
				gateDao.update(gate);
				logger.info("New frequency: " + updateFrequency);
			} catch (Exception e) {
				TFNewFrequency.setPromptText("Please enter a correct value");
			}
			this.setListFromDb();
			TFNewFrequency.clear();
			TFNewFrequency.setPromptText("New frequency");
			LabelFrequency.setText(userManager.toString());
			userManagerDao.update(userManager);
		}
    }
    

    @FXML
    void RequestOpen(ActionEvent event) {
    	Remote selectedRemote = LVAllRemotes.getSelectionModel().getSelectedItem();
    	if(selectedRemote != null) {
    		if (selectedRemote.sendRequest(gate)) {
    			if (ToggleGate.getText() == "Open") {
    				ToggleGate.setText("Closed");
        			ToggleGate.setSelected(false);
        			logger.info("Closing gate");
				}else {
					ToggleGate.setText("Open");
	    			ToggleGate.setSelected(true);
	    			logger.info("Opening gate");
				}
    			logger.info("Good frequency & RC is active");
			}else {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Warning");
		        alert.setHeaderText("This is not allowed");
		        alert.setContentText("Either the frequency is incorrect or the remote is not active.");
		        alert.showAndWait();
			}
    	}else {
            // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No remote selected");
	        alert.setContentText("Please select a remote in the table.");
	        alert.showAndWait();
    	}
    	this.setListFromDb();
    }
    

    @FXML
    void Add10Remotes(ActionEvent event) {
    	for (int i = 0; i < 10; i++) {
    		Remote remote = new Remote();
        	this.getRemoteData().add(remote);
        	remoteDao.create(remote);
		}
    }

}
