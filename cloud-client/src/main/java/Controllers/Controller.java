package Controllers;


import Communication.MyClientServer;
import WorkingWithMessage.SendMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //Login window
    public TextField textField_login;
    public PasswordField testField_pass;


    //work window
    //client
    public ProgressBar pb_client;
    public TableView<FileForTable> table_client;
    public TableColumn<FileForTable,String> table_clientName;
    public TableColumn<FileForTable,String> table_clientSize;
    public TableColumn<FileForTable,String> table_clientDate;
    public ObservableList<FileForTable> fileData = FXCollections.observableArrayList();

    //server
    public TableColumn table_serverName;
    public TableColumn table_serverSize;
    public TableColumn table_serverDate;



    private MyClientServer messageService;
    private SendMessage sendMessage;

    private String client;

    public void setClient(String client) {
        this.client = client;
    }

    public void shutdown() {
        //System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            this.messageService = new MyClientServer(this);
            this.sendMessage = new SendMessage(this.messageService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //button login
    public void login_buttonSignIn(ActionEvent actionEvent) {
        sendMessage.sendSighIn(textField_login.getText(), testField_pass.getText());
    }

    //button clients
    public void button_sendToService(ActionEvent actionEvent) {
        File file = new File("cloud-client/storage/1.txt");

        try {
            sendMessage.sendFileToServer("user1",file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
