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

public class MoneyTransfer implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private SortedMap<Integer, Account> clients;
    private int curNumber1;
    private Account curClient1;
    private int curNumber2;
    private Account curClient2;

    @FXML private ChoiceBox<Integer> picClient1;
    @FXML private ChoiceBox<Integer> picClient2;
    @FXML private TextArea client1;
    @FXML private TextArea client2;
    @FXML private TextField amount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            clients= FileHandler.readClients();
        } catch (FileNotFoundException e) {
            System.out.println("File Clients.csv was not found. Please contact your system admin.");
        }
        picClient1.getItems().addAll(clients.keySet());
        picClient2.getItems().addAll(clients.keySet());
    }

    public void back(ActionEvent actionEvent) throws IOException { //change scene to AdminPanel
        root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void load1(ActionEvent actionEvent) { //load client1
        updateBlock1(picClient1.getValue());
        curNumber1 = picClient1.getValue();
        curClient1 = clients.get(curNumber1);
    }

    public void load2(ActionEvent actionEvent) { //load client2
        updateBlock2(picClient2.getValue());
        curNumber2 = picClient2.getValue();
        curClient2 = clients.get(curNumber2);
    }

    public void updateBlock1(int accNumber){
        client1.setText(clients.get(accNumber).toTextBlock("ALL"));
    }
    public void updateBlock2(int accNumber){
        client2.setText(clients.get(accNumber).toTextBlock("ALL"));
    }

    public void transfer(ActionEvent actionEvent) throws IOException { //transfer money, if not possible send error
        double transAmount=Double.parseDouble(amount.getText());
        if(transAmount>curClient1.getMoney()){
            errorDialog("Not enough funds!",actionEvent);
            System.out.println("Error");
        }else{
            curClient1.setMoney(curClient1.getMoney()-transAmount);
            curClient2.setMoney(curClient2.getMoney()+transAmount);
            updateBlock1(curNumber1);
            updateBlock2(curNumber2);
            FileHandler.saveClients(clients);
        }
    }

    public void errorDialog(String string, ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        alert.setHeaderText(string);
        alert.showAndWait();
    }
}
