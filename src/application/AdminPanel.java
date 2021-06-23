package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPanel {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void openAccManager(ActionEvent actionEvent) throws IOException { //change scene to AccountManager
        root = FXMLLoader.load(getClass().getResource("AccountManager.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openMoneyTransfer(ActionEvent actionEvent) throws IOException { //change scene to MoneyTransfer
        root = FXMLLoader.load(getClass().getResource("MoneyTransfer.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openDepoWith(ActionEvent actionEvent) throws IOException { //change scene to DepoWith
        root = FXMLLoader.load(getClass().getResource("DepoWith.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openLookup(ActionEvent actionEvent) throws IOException { //change scene to Lookup
        root = FXMLLoader.load(getClass().getResource("Lookup.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void logout(ActionEvent actionEvent) throws IOException { //clean current user, change scene
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
