package Controllers;


//import communication.SendMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import networkCommunication.IService;
//import networkCommunication.MyServerClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //Login window
    public TextField textField_login;
    public PasswordField testField_pass;


    //work window
    //client
    public TableColumn table_clientName;
    public TableColumn table_clientSize;
    public TableColumn table_clientDate;
    public ProgressBar pb_client;
    //server
    public TableColumn table_serverName;
    public TableColumn table_serverSize;
    public TableColumn table_serverDate;


//    private IService messageService;
//    private SendMessage sendMessage;

    public void shutdown() {
        //System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        try{
////            this.messageService = new MyServerClient(this);
////            //this.getMessage = new GetMessage(this.messageService,this);
////            this.sendMessage = new SendMessage(this.messageService);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    //button login
    public void login_buttonSignIn(ActionEvent actionEvent) {
        ChangeStage.changeStageDo((Stage) testField_pass.getScene().getWindow(), "/workInterface.fxml",
                "Work window "+textField_login.getText());
    }

    //button clients
    public void button_sendToService(ActionEvent actionEvent) {
    }

    public void button_delete(ActionEvent actionEvent) {
    }

    public void button_edit(ActionEvent actionEvent) {
    }

    //button server
    public void button_sendToClient(ActionEvent actionEvent) {
    }

    public void button_exit(ActionEvent actionEvent) {
    }



}
