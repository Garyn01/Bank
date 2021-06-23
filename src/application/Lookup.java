package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedMap;

public class Lookup implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private SortedMap<Integer, Account> clients;
    private Account conditionValues = new Account();

    @FXML private TextArea conditions;
    @FXML private TextField value;
    @FXML private ChoiceBox<String> field;
    @FXML private ChoiceBox<String> condition;
    @FXML private TextArea results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            clients= FileHandler.readClients();
        } catch (FileNotFoundException e) {
            System.out.println("File Clients.csv was not found. Please contact your system admin.");
        }
        field.getItems().addAll("name","surname","PESEL","city","road","money");
        conditionValues.clear();
    }

    public void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addCondition(ActionEvent actionEvent) {
        switch(field.getValue()){
            case "name" -> conditionValues.setName(value.getText());
            case "surname" -> conditionValues.setSurname(value.getText());
            case "PESEL" -> conditionValues.setPESEL(Integer.parseInt(value.getText()));
            case "city" -> conditionValues.setCity(value.getText());
            case "road" -> conditionValues.setRoad(value.getText());
            case "money" ->conditionValues.setMoney(Double.parseDouble(value.getText()));
        }
        conditions.setText(conditionValues.toTextBlock("ALL"));
    }

    public void search(ActionEvent actionEvent) { //TODO add the "not equal" options
        StringBuilder temp = new StringBuilder();
        for (Map.Entry<Integer, Account> entry : clients.entrySet()) {
            Account account = entry.getValue();
            if (conditionValues.compareTo(account)) {
                temp.append(account.toTextBlock("ALL")).append("\n\n");//account.getName()).append(' ').append(account.getSurname());
            }
        }
        results.setText(temp.toString());
    }

    public void clear(ActionEvent actionEvent) {
        conditionValues.clear();
        conditions.setText("");
        results.setText("");
    }
}
