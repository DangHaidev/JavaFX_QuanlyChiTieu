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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField su_email;

    @FXML
    private Button su_btn;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_pass;
    @FXML
    public ComboBox<?> comboBox;
    private String[] question = {"a","b"};
    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @Override
    public void initialize(URL location, ResourceBundle resource)
    {
        comboxChance();
    }
    public void comboxChance() {
        List<String> listQ = new ArrayList<>();

        for(String data : question)
        {
            listQ.add(data);
        }
        ObservableList ListData = FXCollections.observableArrayList(listQ);
        comboBox.setItems(ListData);
    }
    public void loginBtn() {
        
    }
    public void regBtn() {
        if(su_email.getText().isEmpty() || su_pass.getText().isEmpty() ||
        comboBox.getSelectionModel().getSelectedItem() == null
        || su_answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error message!");
            alert.setHeaderText(null);
            alert.setContentText("Please  fill any fields!");
            alert.showAndWait();
        } else {
            // check username is already record
            String regData = "INSERT INTO customer (email,pass,question,answer,date)"
                    + "VALUES (?,?,?,?,?)";
            connect = Database.connecDB();
            try {
                String checEmailuser = "SELECT email FROM customer WHERE email = '" + su_email.getText() +"' ";
                prepare = connect.prepareStatement(checEmailuser);
                result = prepare.executeQuery();
                if(result.next())
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error message!");
                    alert.setHeaderText(null);
                    alert.setContentText(su_email.getText() +  " is already");
                    alert.showAndWait();
                } else if (su_pass.getText().length() < 8)
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error message!");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password, at least 8");
                    alert.showAndWait();
                }  else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_email.getText());
                    prepare.setString(2, su_pass.getText());
                    prepare.setString(3, (String) comboBox.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("message!");
                    alert.setHeaderText(null);
                    alert.setContentText("Successtion");
                    alert.showAndWait();

                    su_email.setText("");
                    su_pass.setText("");
                    comboBox.getSelectionModel().clearSelection();
                    su_answer.setText("");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
