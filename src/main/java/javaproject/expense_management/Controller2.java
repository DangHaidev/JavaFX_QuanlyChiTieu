package javaproject.expense_management;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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

    public ObservableList<DailyData> addDailyData() {
        ObservableList<DailyData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM daily";
        connection = Database.connecDB();

        DailyData dData;
        try {
            prepare = connection.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next())
            {
//                DailyData(String dailyID, String Type, String LoaiTC, int SoTien, String TaiKhoan,String Note , Date
//                date)
                dData = new DailyData(result.getString("daily_ID"),result.getString("Loai"),result.getString("TheLoai"),
                        result.getInt("SoTien"),result.getString("TaiKhoan"),
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addDailyDisplayData();
    }
}
