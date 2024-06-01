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
import java.util.Optional;
import java.util.ResourceBundle;

public class editController implements Initializable {

    @FXML
    private TextArea edit_note;

    @FXML
    private Button edit_save;

    @FXML
    private DatePicker edit_time;

    @FXML
    private ComboBox<String> edid_type;

    @FXML
    private ComboBox<String> edit_account;

    @FXML
    private TextField edit_typeTC;

    @FXML
    private TextField edit_price;
    private Alert alert;
    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    public void DisplayInformation() {
        edit_typeTC.setText(DailyData.temp_LoaiThuChi);
        edit_price.setText(DailyData.temp_SoTien);
        edit_note.setText(DailyData.temp_Note);
        edid_type.getSelectionModel().select(DailyData.temp_Type);
        edit_account.getSelectionModel().select(DailyData.temp_TaiKhoan);
    }
    public void typeList() {
        List<String> Typelist = new ArrayList<>();
        for(String data : ListData.type)
        {
            Typelist.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(Typelist);
        edid_type.setItems(listData);
    }
    public void accountList() {
        List<String> Typelist = new ArrayList<>();
        for(String data : ListData.account)
        {
            Typelist.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(Typelist);
        edit_account.setItems(listData);
    }
    public void update()
    {
        if(edid_type.equals(DailyData.temp_Type)
                && edit_account.equals(DailyData.temp_TaiKhoan)
                && edit_price.equals(DailyData.temp_SoTien)
                && edit_note.equals(DailyData.temp_Note)
                && edit_typeTC.equals(DailyData.temp_LoaiThuChi))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error message!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chỉnh sửa thông tin!");
            alert.showAndWait();
        } else
            try {
                // Chuyển đổi từ LocalDate sang java.sql.Date
                java.util.Date date = java.util.Date.from(edit_time.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                // Tạo câu lệnh SQL với các tham số
                String update = "UPDATE daily1 SET Loai = ?, TheLoai = ?, SoTien = ?, TaiKhoan = ?, Note = ?, Time = ? WHERE Note = ? AND SoTien = ?";

                // Kết nối với cơ sở dữ liệu
                connection = Database.connecDB();

                try {
                    // Sử dụng PreparedStatement để thiết lập các tham số
                    prepare = connection.prepareStatement(update);
                    prepare.setString(1, edid_type.getSelectionModel().getSelectedItem());
                    prepare.setString(2, edit_typeTC.getText());
                    prepare.setString(3, edit_price.getText()); // Giả sử SoTien là kiểu int
                    prepare.setString(4, edit_account.getSelectionModel().getSelectedItem());
                    prepare.setString(5, edit_note.getText());
                    prepare.setDate(6, sqlDate);
                    prepare.setString(7, DailyData.temp_Note);
                    prepare.setString(8, DailyData.temp_SoTien);

                    // Thực thi câu lệnh UPDATE
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("message!");
                    alert.setHeaderText(null);
                    alert.setContentText("Cập nhật thành công!");
                    alert.showAndWait();

                    // Xóa các trường sau khi cập nhật
                    clearField();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Đảm bảo đóng các tài nguyên sau khi sử dụng
                    if (prepare != null) prepare.close();
                    if (connection != null) connection.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }
    public void clearField() {
        edid_type.getSelectionModel().clearSelection();
        edit_typeTC.clear();
        edit_account.getSelectionModel().clearSelection();
        edit_price.clear();
        edit_time.getEditor().clear();
        edit_note.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DisplayInformation();
        typeList();
        accountList();
    }
}
