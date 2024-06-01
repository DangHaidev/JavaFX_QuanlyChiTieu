package javaproject.expense_management;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;


public class Controller2 implements Initializable {
    @FXML
    private TableColumn<DailyData, String> col_note;

    @FXML
    private TableColumn<DailyData, Date> col_date;
    @FXML
    private Button thuchiBtn;

    @FXML
    private TableColumn<DailyData, String> col_account;

    @FXML
    private TableColumn<DailyData, String> col_typeTC;

    @FXML
    private TableView<DailyData> daily_tableview;

    @FXML
    private TableColumn<DailyData, String> col_type;

    @FXML
    private TableColumn<DailyData, Integer> col_Sotien;

    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Alert alert;
    @FXML
    private ImageView imageAva;
    private Image image;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField test_Tien;
    @FXML
    private TextField test_Note;
    @FXML
    private Label date_time;

    public ObservableList<DailyData> addDailyData() {
        ObservableList<DailyData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM daily1";
        connection = Database.connecDB();

        DailyData dData;
        try {
            prepare = connection.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next())
            {
//                DailyData(String dailyID, String Type, String LoaiTC, int SoTien, String TaiKhoan,String Note , Date
//                date)
                dData = new DailyData(result.getString("id"),result.getString("Loai"),result.getString("TheLoai"),
                        result.getString("SoTien"),result.getString("TaiKhoan"),
                        result.getString("Note"),result.getDate("Time"));

                listData.add(dData);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<DailyData> addDailyListData;
    public void addDailyDisplayData() {
        addDailyListData = addDailyData();
        col_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col_typeTC.setCellValueFactory(new PropertyValueFactory<>("LoaiThuChi"));
        col_Sotien.setCellValueFactory(new PropertyValueFactory<>("SoTien"));
        col_account.setCellValueFactory(new PropertyValueFactory<>("TaiKhoan"));
        col_note.setCellValueFactory(new PropertyValueFactory<>("Note"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        daily_tableview.setItems(addDailyListData);
    }

    public void ThemThuChi() {
        try {
            Parent root2 = FXMLLoader.load(Main.class.getResource("themthuchi.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root2,550,650);
            stage.setTitle("thu chi");
            stage.setScene(scene);
            stage.show();
//            addDailyDisplayData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chanceAvatar() {
        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mở thư mục", "*.jpg", "*.png"));
        File file = open.showOpenDialog(imageAva.getScene().getWindow());

        if( file != null) {
            ListData.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 130, 130, false, true);
            imageAva.setImage(image);
        }
    }
    public void DeleteRecord() {
        String deleteData = "DELETE FROM daily1 WHERE Note = '"
                + test_Note.getText() + "'" + "AND SoTien = '" + test_Tien.getText() + "'";
        connection = Database.connecDB();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(deleteData);

            String checkData = "SELECT Note FROM daily1 "
                    + "WHERE Note = '" + test_Tien.getText()
                    +  "'";

            prepare = connection.prepareStatement(checkData);
            result = prepare.executeQuery();

            //
            if (result.next()) {
                String deleteGrade = "DELETE FROM daily1 WHERE "
                        + "Note = '" + test_Tien.getText() + "'";

                statement = connection.createStatement();
                statement.executeUpdate(deleteGrade);

            }//

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Xóa thành công!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void test()
    {
        DailyData test = daily_tableview.getSelectionModel().getSelectedItem();
        int num = daily_tableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        test_Note.setText(test.getNote());
        test_Tien.setText(test.getSoTien());
    }

        public void editThuChi() throws IOException {
        DailyData dData = daily_tableview.getSelectionModel().getSelectedItem();
        int num = daily_tableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.setTitle("Hãy chọn một dòng!");
        }
        DailyData.temp_Type = dData.getType();
        DailyData.temp_LoaiThuChi = dData.getLoaiThuChi();
        DailyData.temp_SoTien = dData.getSoTien();
        DailyData.temp_TaiKhoan = dData.getTaiKhoan();
        DailyData.temp_Note = dData.getNote();
        DailyData.temp_date = dData.getDate();

        Parent root3 = FXMLLoader.load(Main.class.getResource("editThuChi.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root3,550,650);
        stage.setTitle("Edit form");
        stage.setScene(scene);
        stage.show();
    }
//    public void runTime() {
//        new Thread() {
//            public void run() {
//                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
//                while (true) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    Platform.runLater(() -> {
//                        date_time.setText(format.format(new Date()));
//                    });
//                }
//            }
//        }.start();
//    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addDailyDisplayData();
//        deleteButton.setOnAction(e -> {
//            DailyData selectItem =  daily_tableview.getSelectionModel().getSelectedItem();
//            daily_tableview.getItems().remove(selectItem);
//        });
    }
}
