package javaproject.expense_management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private TextField si_email;
    @FXML
    private PasswordField si_pass;

//    @FXML
//    private Button su_btn;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_pass;
    @FXML
    public ComboBox<?> comboBox;
    @FXML
    public ComboBox<?> comboBox2;
    private String[] question = {"a","b"};
    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
//    @FXML
//    private Button login_in_su_form;
    @FXML
    private AnchorPane login_form;
    @FXML
    private AnchorPane su_form;
    @FXML
    private AnchorPane forgotform;
    @FXML
    private AnchorPane login;
    @FXML
    private AnchorPane newPass;
    @FXML
    private TextField fg_email;
    @FXML
    private TextField fg_answer;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmPass;
    @FXML
    private Button sign_in;

    @Override
    public void initialize(URL location, ResourceBundle resource)
    {
        comboxChance();
    }
    public void loginBtn_in_signup()
    {
        login_form.setVisible(true);
        su_form.setVisible(false);
    }
    public void suBtn_in_loginform()
    {
        login_form.setVisible(false);
        su_form.setVisible(true);
    }
    public void forgotBtn()
    {
        login.setVisible(false);
        forgotform.setVisible(true);
    }
    public void backBtn()
    {
        forgotform.setVisible(false);
        login.setVisible(true);
    }
    public void backBtn1()
    {
        forgotform.setVisible(true);
        newPass.setVisible(false);
    }
    public void comboxChance() {
        List<String> listQ = new ArrayList<>();

        for(String data : question)
        {
            listQ.add(data);
        }
        ObservableList ListData = FXCollections.observableArrayList(listQ);
        comboBox.setItems(ListData);
        comboBox2.setItems(ListData);
    }

    // login in login form
    public void loginBtn() {
        if(si_email.getText().isEmpty() || si_pass.getText().isEmpty() )
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error message!");
            alert.setHeaderText(null);
            alert.setContentText("Invailable username/password!");
            alert.showAndWait();
        } else
        {
            String selectData = "SELECT email, pass FROM customer WHERE email = ? and pass = ? ";

             connect = Database.connecDB();
             try {
                 prepare = connect.prepareStatement(selectData);
                 prepare.setString(1,si_email.getText());
                 prepare.setString(2,si_pass.getText());
                 result = prepare.executeQuery();
            // if success, proceed to another form
                 if(result.next())
                 {
                     alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("message!");
                     alert.setHeaderText(null);
                     alert.setContentText("Success!");
                     alert.showAndWait();

                     Parent root = FXMLLoader.load(Main.class.getResource("test.fxml"));
                     Stage stage = new Stage();
                     Scene scene = new Scene(root,1120,700);
                     stage.setTitle("Program");
                     stage.setScene(scene);
                     stage.show();

                     sign_in.getScene().getWindow().hide();

                 } else // if not, error message
                 {
                     alert = new Alert(Alert.AlertType.ERROR);
                     alert.setTitle("error message!");
                     alert.setHeaderText(null);
                     alert.setContentText("Incorrect username/password!");
                     alert.showAndWait();
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             }

        }
    }
    public void proceedBtn()
    {
        if(fg_email.getText().isEmpty() || fg_answer.getText().isEmpty()
        || comboBox2.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error message!");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username/password!");
            alert.showAndWait();
        } else
        {
            String selectData = "SELECT email, question, answer FROM customer WHERE email = ? AND question = ? AND answer = ?";
            connect = Database.connecDB();
            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fg_email.getText());
                prepare.setString(2,(String)comboBox2.getSelectionModel().getSelectedItem());
                prepare.setString(3,fg_answer.getText());
                result = prepare.executeQuery();

                if(result.next())
                {
                    newPass.setVisible(true);
                    forgotform.setVisible(false);
                } else
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error message!");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect information!");
                    alert.showAndWait();
                }
            } catch (Exception e){ e.printStackTrace();}

        }

    }
    public void changePassBtn()
    {
        if(confirmPass.getText().isEmpty() || newPassword.getText().isEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error message!");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all!");
            alert.showAndWait();
        } else {

            if(newPassword.getText().equals(confirmPass.getText()))
            {
                String getDate = "SELECT date FROM customer WHERE email = '"
                        + fg_email.getText() + "'";
                connect = Database.connecDB();

                try {
                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();
                    String date = null;
                    if(result.next())
                    {
                        date = result.getString("date");
                    }
//                    String updatePass = "UPDATE customer SET pass = '" + newPassword.getText() +
//                            "', question = '" + comboBox2.getSelectionModel().getSelectedItem() +
//                            "' , answer = '"
//                            + fg_answer.getText() "', date = '"
//                            + date + "' WHERE email = '"
//                            +  fg_email.getText() + "'";
                    String updatePass = "UPDATE customer SET pass = '" + newPassword.getText() +
                            "', question = '" + comboBox2.getSelectionModel().getSelectedItem() +
                            "', answer = '" + fg_answer.getText() +
                            "', date = '" + date +
                            "' WHERE email = '" + fg_email.getText() + "'";

                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("message!");
                    alert.setHeaderText(null);
                    alert.setContentText("Success change password!!");
                    alert.showAndWait();


                    newPass.setVisible(false);
                    forgotform.setVisible(false);
                    login.setVisible(true);
                    // clear fields
                    newPassword.setText("");
                    confirmPass.setText("");
                    comboBox2.getSelectionModel().clearSelection();
                    fg_email.setText("");
                    fg_answer.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error message!");
                alert.setHeaderText(null);
                alert.setContentText("Not match !!");
                alert.showAndWait();
            }


        }
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
