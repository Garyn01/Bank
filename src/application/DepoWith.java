package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.SortedMap;

public class DepoWith implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private SortedMap<Integer, Account> clients;
    private int curNumber;
    private Account curClient;

    @FXML private ChoiceBox<Integer> picClient;
    @FXML private TextArea client;
    @FXML private TextField amount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            clients= FileHandler.readClients();
        } catch (FileNotFoundException e) {
            System.out.println("File Clients.csv was not found. Please contact your system admin.");
        }
        picClient.getItems().addAll(clients.keySet());
    }

    public void back(ActionEvent actionEvent) throws IOException { //change scene to AdminPanel
        root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void load(ActionEvent actionEvent) { //load client to textarea
        curNumber=picClient.getValue();
        client.setText(clients.get(curNumber).toTextBlock("ALL"));
        curClient=clients.get(curNumber);
    }

    public void deposit(ActionEvent actionEvent) throws IOException { //deposit money to loaded client
        curClient.setMoney(curClient.getMoney()+Double.parseDouble(amount.getText()));
        FileHandler.saveClients(clients);
        client.setText(clients.get(curNumber).toTextBlock("ALL"));
    }

    public void withdraw(ActionEvent actionEvent) throws IOException { //withdraw money from loaded client, if not possible send error
        double transAmount = Double.parseDouble(amount.getText());
        if(transAmount>curClient.getMoney()){
            alertDialog("Not enough funds!",actionEvent);
        }else{
            curClient.setMoney(curClient.getMoney()-transAmount);
            FileHandler.saveClients(clients);
            client.setText(clients.get(curNumber).toTextBlock("ALL"));
        }
    }

    public void alertDialog(String string, ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        alert.setHeaderText(string);
        alert.showAndWait();
    }
}
