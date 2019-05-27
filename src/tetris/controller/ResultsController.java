package tetris.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import results.Results;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {

    @FXML private TableView<Results> tableView;

    @FXML private TableColumn<Results, Integer> id;
    @FXML private TableColumn<Results, String> name;
    @FXML private TableColumn<Results, Integer> score;
    @FXML private TableColumn<Results, LocalDate> date;

    List<results.Results> resultsList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        id.setCellValueFactory(new PropertyValueFactory<Results, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Results, String>("name"));
        score.setCellValueFactory(new PropertyValueFactory<Results, Integer>("score"));
        date.setCellValueFactory(new PropertyValueFactory<Results, LocalDate>("date"));

        tableView.setItems(getResults());
    }

    private ObservableList<Results> getResults() {
        resultsList = Controller.getHighScoreList();
        ObservableList<Results> results = FXCollections.observableArrayList();
        results.removeAll();
        results.addAll(resultsList);

        return results;
    }

    public void setResultsList(List<Results> resultsList) {
        this.resultsList = resultsList;
        tableView.setItems(getResults());
    }

    public void changeScreen(Stage window) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("results.fxml"));
        Scene scene = new Scene(root);

        window.setScene(scene);
        window.show();

    }
}
