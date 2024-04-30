package javaproject.expense_management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller3 implements Initializable {
    @FXML
    private TextArea in4_note;

    @FXML
    private ComboBox<?> in4_account;

    @FXML
    private Button in4_save;

    @FXML
    private DatePicker in4_time;

    @FXML
    private ComboBox<?> in4_typeTC;

    @FXML
    private TextField in4_price;

    @FXML
    private ComboBox<?> in4_type;
    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Alert alert;

    public void saveBtn() {
        if( in4_type.getSelectionModel().getSelectedItem() == null
        || in4_typeTC.getSelectionModel().getSelectedItem() == null
        || in4_account.getSelectionModel().getSelectedItem() == null
        || in4_price.getText().isEmpty())
        {
            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
            alert.showAndWait();
        }
        else {

        }
    }

    public void typeList() {
        List<String> Typelist = new ArrayList<>();
        for(String data : ListData.type)
        {
            Typelist.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(Typelist);
        in4_type.setItems(listData);
    }
    public void typeTcList() {
        List<String> Typelist = new ArrayList<>();
        for(String data : ListData.typeTc)
        {
            Typelist.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(Typelist);
        in4_typeTC.setItems(listData);
    }
    public void accountList() {
        List<String> Typelist = new ArrayList<>();
        for(String data : ListData.account)
        {
            Typelist.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(Typelist);
        in4_account.setItems(listData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeList();
        typeTcList();
        accountList();
    }
}
