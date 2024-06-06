package javaproject.expense_management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller3 implements Initializable {
    @FXML
    private ComboBox<?> cateloryCB;
    @FXML
    private TextArea in4_note;

    @FXML
    private ComboBox<?> in4_account;

    @FXML
    private Button in4_save;

    @FXML
    private DatePicker in4_time;

    @FXML
    private TextField in4_typeTC;

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
        || in4_typeTC.getText().isEmpty()
        || in4_account.getSelectionModel().getSelectedItem() == null
        || in4_price.getText().isEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error message!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
            alert.showAndWait();
        }
        else {

            try {
                String insertData = "INSERT INTO daily1 (Loai,TheLoai,SoTien,TaiKhoan,Note,Time,email)"
                        + "VALUES (?,?,?,?,?,?,?)";

                connection = Database.connecDB();
                prepare = connection.prepareStatement(insertData);
                prepare.setString(1,(String) in4_type.getSelectionModel().getSelectedItem());
                prepare.setString(2,in4_typeTC.getText());
                prepare.setString(3,in4_price.getText());
                prepare.setString(4,(String) in4_account.getSelectionModel().getSelectedItem());
                prepare.setString(5, in4_note.getText());
                java.util.Date date =
                        java.util.Date.from(in4_time.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(6,String.valueOf(sqlDate));
                prepare.setString(7, ListData.temp_user);
                prepare.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("message!");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thành công!");
                alert.showAndWait();
                clearField();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void clearField() {
        in4_type.getSelectionModel().clearSelection();
        in4_typeTC.clear();
        in4_account.getSelectionModel().clearSelection();
        in4_price.clear();
        in4_time.getEditor().clear();
        in4_note.clear();
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
        accountList();
    }
}
