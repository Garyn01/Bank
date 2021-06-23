package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.SortedMap;

public class AccountManager implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private SortedMap<Integer, Account> clients;

    @FXML private ChoiceBox<Integer> picNumber;
    @FXML private TextField curNumber;
    @FXML private TextField curName;
    @FXML private TextField curSurname;
    @FXML private TextField curPESEL;
    @FXML private TextField curCity;
    @FXML private TextField curRoad;
    @FXML private TextField curMoney;
    @FXML private DialogPane accExists;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            clients= FileHandler.readClients();
        } catch (FileNotFoundException e) {
            System.out.println("File Clients.csv was not found. Please contact your system admin.");
        }
        picNumber.getItems().addAll(clients.keySet());
    }

    public void load(ActionEvent actionEvent) { //load data into text fields
        Account temp = clients.get(picNumber.getValue());
        curNumber.setText(picNumber.getValue().toString());
        curName.setText(temp.getName());
        curSurname.setText(temp.getSurname());
        curPESEL.setText(temp.getPESEL().toString());
        curCity.setText(temp.getCity());
        curRoad.setText(temp.getRoad());
        curMoney.setText(String.valueOf(temp.getMoney()));
    }

    public void update(ActionEvent actionEvent) throws IOException { //update client with the number (ask before doing so)/create a new one with it
                                                                     //TODO add a quick account number change option
        if(clients.containsKey(Integer.parseInt(curNumber.getText()))){
            if(choiceDialog("Client exists","Client exists, do you want to update them?",actionEvent)) return;
        }
        Account temp = new Account(curName.getText(), curSurname.getText(),curPESEL.getText(),curCity.getText(),curRoad.getText(),curMoney.getText());
        clients.put(Integer.parseInt(curNumber.getText()),temp);
        picNumber.getItems().setAll(clients.keySet());
        FileHandler.saveClients(clients);
    }

    public void back(ActionEvent actionEvent) throws IOException { //change scene to AdminPanel
        root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void remove(ActionEvent actionEvent) throws IOException {
        if(choiceDialog("Remove client","Are you sure you want to remove that client?", actionEvent)) return;
        clients.remove(Integer.parseInt(curNumber.getText()));
        picNumber.getItems().setAll(clients.keySet());
        FileHandler.saveClients(clients);
        alertDialog("Success","Client removed.",actionEvent);
    }

    public boolean choiceDialog(String title, String header, ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(header);
        ButtonType buttonTypeOne = new ButtonType("YES");
        ButtonType buttonTypeTwo = new ButtonType("NO");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() != buttonTypeOne;
    }

    public void alertDialog(String title, String string, ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        alert.setHeaderText(string);
        alert.showAndWait();
    }
}
