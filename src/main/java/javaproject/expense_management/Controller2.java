package javaproject.expense_management;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.*;

//
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Controller2 implements Initializable {
    @FXML
    private TextField search;
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
    private TextField test_Tien;
    @FXML
    private TextField test_Note;
    @FXML
    private Label date_time;
    @FXML
    private Label nameUser;

    @FXML
    private BarChart<?, ?> barchart;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private AnchorPane ThuChiBoard;
    @FXML
    private AnchorPane databoard;
    @FXML
    private AnchorPane thongKeBoard;
    @FXML
    private Button HomeButton;
    @FXML
    private Button ThuChiButton;
    @FXML
    private Label CashMoney;
    @FXML
    private Label BankMoney;
    @FXML
    private Label home_tienThu;
    @FXML
    private Label home_tienChi;
    @FXML
    private Button logout_btn;
    @FXML
    private DatePicker date_box1;

    @FXML
    private DatePicker date_box2;
    @FXML
    private ComboBox<String> Loai_box;

    @FXML
    private ComboBox<String> Taikhoan_box;
    @FXML
    private TableColumn<DailyData, String> Sloai;

    @FXML
    private TableColumn<DailyData, String> Snote;

    @FXML
    private TableColumn<DailyData, Integer> Ssotien;

    @FXML
    private TableColumn<DailyData, String> Staikhoan;

    @FXML
    private TableColumn<DailyData, String> Stheloai;

    @FXML
    private TableColumn<DailyData, Date> Stime;

    @FXML
    private TableView<DailyData> thongkeTable;
    @FXML
    private Button ThongKeBtn;
    @FXML
    private ImageView search_icon;
    public ObservableList<DailyData> addDailyData() {
        ObservableList<DailyData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM daily1 where email = ?";
        connection = Database.connecDB();
        DailyData dData;
        try {
            prepare = connection.prepareStatement(selectData);
            prepare.setString(1, ListData.temp_user);
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
    public void DeleteRecord() {
        String deleteData = "DELETE FROM daily1 WHERE Note = '"
                + test_Note.getText() + "'" + "AND SoTien = '" + test_Tien.getText() + "'" + "AND email = '" + ListData.temp_user + "'";
        connection = Database.connecDB();
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa bản ghi này?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                statement = connection.createStatement();
                statement.executeUpdate(deleteData);

                String checkData = "SELECT Note FROM daily1 "
                        + "WHERE Note = '" + test_Tien.getText()
                        + "'";

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
            }

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
    private void startClock() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    Platform.runLater(() -> date_time.setText(format.format(new Date())));
                }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void displayBarchart() {
        barchart.getData().clear();
//        "SELECT Time , SoTien FROM daily1 WHERE Loai = 'Thu nhập' ";
        String sql = "SELECT DATE_FORMAT(Time, '%m-%d') AS Month, SUM(SoTien) AS SoTien " +
                "FROM daily1 WHERE Loai = 'Thu nhập' and email = ?" +
                "GROUP BY DATE_FORMAT(Time, '%m-%d') " +
                "ORDER BY DATE_FORMAT(Time, '%m-%d')  LIMIT 6";
//        "SELECT Time , SoTien FROM daily1 WHERE Loai = 'Chi tiêu' ";
        String sql2 = "SELECT DATE_FORMAT(Time, '%m-%d') AS Month, SUM(SoTien) AS SoTien " +
                "FROM daily1 WHERE Loai = 'Chi tiêu' and email = ?" +
                "GROUP BY DATE_FORMAT(Time, '%m-%d') " +
                "ORDER BY DATE_FORMAT(Time, '%m-%d')  LIMIT 6";

        connection = Database.connecDB();
        try
        {
            XYChart.Series chart = new XYChart.Series<>();
            XYChart.Series chart2 = new XYChart.Series<>();
            chart.setName("Thu nhập");
            chart2.setName("Chi tiêu");
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, ListData.temp_user);
            result = prepare.executeQuery();

            while (result.next())
            {
                chart.getData().add(new XYChart.Data(result.getString(1),result.getInt(2)));
            }
            try
            {
                prepare = connection.prepareStatement(sql2);
                prepare.setString(1, ListData.temp_user);
                result = prepare.executeQuery();
               while (result.next()){
                    chart2.getData().add(new XYChart.Data(result.getString(1),result.getInt(2)));
                }
                barchart.getData().addAll(chart,chart2);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayLineChart()
    {
        lineChart.getData().clear();
        String sql = "SELECT " +
                "MONTH(Time) AS Month, " +
                "SUM(CASE WHEN Loai = 'Thu nhập' THEN SoTien ELSE 0 END) - " +
                "SUM(CASE WHEN Loai = 'Chi tiêu' THEN SoTien ELSE 0 END)  " +
                "FROM daily1  where email = ?" +
                "GROUP BY MONTH(Time) " +
                "LIMIT 5";

        connection = Database.connecDB();
        try {
            XYChart.Series chart = new XYChart.Series();
            chart.setName("Số dư");
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, ListData.temp_user);
            result = prepare.executeQuery();
            while (result.next())
            {
                chart.getData().add(new XYChart.Data<>(result.getString(1),result.getInt(2)));
            }
            lineChart.getData().add(chart);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void displayCash()
    {
        String sql = "SELECT " +
                "(sum(case when Loai = 'Thu nhập' and TaiKhoan = 'Tiền mặt' Then SoTien else 0 end ) - " +
                "sum(case when Loai = 'Chi tiêu' and TaiKhoan = 'Tiền mặt' Then SoTien else 0 end )) as sodu " +
                "FROM daily1 where email = ?";

        connection = Database.connecDB();

        try {
            int countCash = 01;
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, ListData.temp_user);
            result = prepare.executeQuery();
            if (result.next())
            {
                countCash = result.getInt("sodu");
            }
            CashMoney.setText(String.valueOf(countCash));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void displayBankMoney()
    {
        String sql = "SELECT " +
                "(sum(case when Loai = 'Thu nhập' and TaiKhoan = 'Ngân hàng' Then SoTien else 0 end ) - " +
                "sum(case when Loai = 'Chi tiêu' and TaiKhoan = 'Ngân hàng' Then SoTien else 0 end )) as sodu " +
                "FROM daily1 where email = ?";

        connection = Database.connecDB();

        try {
            int countCash = 01;
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, ListData.temp_user);
            result = prepare.executeQuery();
            if (result.next())
            {
                countCash = result.getInt("sodu");
            }
            BankMoney.setText(String.valueOf(countCash));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void displaytotalThu()
    {
        String sql = "select sum(SoTien) as total from daily1 where Loai = 'Thu nhập' and email = ?";

        connection = Database.connecDB();

        try {
            int countCash = 01;
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, ListData.temp_user);
            result = prepare.executeQuery();
            if (result.next())
            {
                countCash = result.getInt("total");
            }
            home_tienThu.setText(String.valueOf(countCash));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void displaytotalChi()
    {
        String sql = "select sum(SoTien) as total from daily1 where Loai = 'Chi tiêu' and email = ?";

        connection = Database.connecDB();

        try {
            int countCash = 01;
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, ListData.temp_user);
            result = prepare.executeQuery();
            if (result.next())
            {
                countCash = result.getInt("total");
            }
            home_tienChi.setText(String.valueOf(countCash));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void typeChange() {
        List<String> listT = new ArrayList<>();

        for(String data : ListData.pickType)
        {
            listT.add(data);
        }
        ObservableList ListData = FXCollections.observableArrayList(listT);
        Loai_box.setItems(ListData);
    }

    public void accountChange() {
        List<String> listA = new ArrayList<>();

        for(String data : ListData.pickAccount)
        {
            listA.add(data);
        }
        ObservableList ListData = FXCollections.observableArrayList(listA);
        Taikhoan_box.setItems(ListData);
    }
    public ObservableList<DailyData> pickData() {
        ObservableList<DailyData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM daily1 WHERE Loai = ? AND TaiKhoan = ? and email = ? AND Time BETWEEN ? AND ?";
        connection = Database.connecDB();

        DailyData pData;
        try{
            java.util.Date date1 = java.util.Date.from(date_box1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
            java.util.Date date2 = java.util.Date.from(date_box2.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());

            prepare = connection.prepareStatement(selectData);
            prepare.setString(1, Loai_box.getSelectionModel().getSelectedItem());
            prepare.setString(2, Taikhoan_box.getSelectionModel().getSelectedItem());
            prepare.setString(3, ListData.temp_user);
            prepare.setDate(4, sqlDate1);
            prepare.setDate(5, sqlDate2);
            result = prepare.executeQuery();

            while (result.next())
            {
                pData = new DailyData(
                        result.getString("id"),
                        result.getString("Loai"),
                        result.getString("TheLoai"),
                        result.getString("SoTien"),
                        result.getString("TaiKhoan"),
                        result.getString("Note"),
                        result.getDate("Time")
                );
                listData.add(pData);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng các tài nguyên sau khi sử dụng
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listData;
    }

    private ObservableList<DailyData> pickDailyData;
    public void pickDailyDisplayData(){
        pickDailyData = pickData();
        Sloai.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Stheloai.setCellValueFactory(new PropertyValueFactory<>("LoaiThuChi"));
        Ssotien.setCellValueFactory(new PropertyValueFactory<>("SoTien"));
        Staikhoan.setCellValueFactory(new PropertyValueFactory<>("TaiKhoan"));
        Snote.setCellValueFactory(new PropertyValueFactory<>("Note"));
        Stime.setCellValueFactory(new PropertyValueFactory<>("date"));

        thongkeTable.setItems(pickDailyData);
    }
    public void logout() {

        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn thoát?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logout_btn.getScene().getWindow().hide();
                Parent root4 = FXMLLoader.load(getClass().getResource("firstUI.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root4);
                stage.setTitle("login");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void welcome()
    {
        nameUser.setText(ListData.temp_user);
    }


    public void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(daily_tableview.getScene().getWindow());

        if (file != null) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Daily Data");

                // Create header row
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < daily_tableview.getColumns().size(); i++) {
                    TableColumn<DailyData, ?> column = daily_tableview.getColumns().get(i);
                    headerRow.createCell(i).setCellValue(column.getText());
                }

                // Create data rows
                ObservableList<DailyData> data = daily_tableview.getItems();
                for (int i = 0; i < data.size(); i++) {
                    Row dataRow = sheet.createRow(i + 1);
                    DailyData dailyData = data.get(i);
                    for (int j = 0; j < daily_tableview.getColumns().size(); j++) {
                        TableColumn<DailyData, ?> column = daily_tableview.getColumns().get(j);
                        Object cellValue = column.getCellObservableValue(dailyData).getValue();
                        if (cellValue != null) {
                            dataRow.createCell(j).setCellValue(cellValue.toString());
                        }
                    }
                }

                // Write the workbook to file
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void exportToExcel1() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(thongkeTable.getScene().getWindow());

        if (file != null) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Daily Data");

                // Create header row
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < thongkeTable.getColumns().size(); i++) {
                    TableColumn<DailyData, ?> column = thongkeTable.getColumns().get(i);
                    headerRow.createCell(i).setCellValue(column.getText());
                }

                // Create data rows
                ObservableList<DailyData> data = thongkeTable.getItems();
                for (int i = 0; i < data.size(); i++) {
                    Row dataRow = sheet.createRow(i + 1);
                    DailyData dailyData = data.get(i);
                    for (int j = 0; j < thongkeTable.getColumns().size(); j++) {
                        TableColumn<DailyData, ?> column = thongkeTable.getColumns().get(j);
                        Object cellValue = column.getCellObservableValue(dailyData).getValue();
                        if (cellValue != null) {
                            dataRow.createCell(j).setCellValue(cellValue.toString());
                        }
                    }
                }

                // Write the workbook to file
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setupSearch() {
        FilteredList<DailyData> filteredData = new FilteredList<>(addDailyListData, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(dailyData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (dailyData.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (dailyData.getLoaiThuChi().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(dailyData.getSoTien()).contains(lowerCaseFilter)) {
                    return true;
                } else if (dailyData.getTaiKhoan().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (dailyData.getNote().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<DailyData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(daily_tableview.comparatorProperty());
        daily_tableview.setItems(sortedData);
    }
    public void hideIcon()
    {
        search_icon.setVisible(false);
    }
    public void unHide()
    {
        search_icon.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome();
        startClock();
        displayBarchart();
        displayLineChart();
        addDailyDisplayData();
        displayCash();
        displayBankMoney();
        displaytotalThu();
        displaytotalChi();
        HomeButton.setOnAction(event -> {
            databoard.setVisible(true);
            ThuChiBoard.setVisible(false);
            thongKeBoard.setVisible(false);
            displayBarchart();
            displayLineChart();
            displayCash();
            displayBankMoney();
            displaytotalThu();
            displaytotalChi();
        });
        ThuChiButton.setOnAction(event -> {
            databoard.setVisible(false);
            ThuChiBoard.setVisible(true);
            thongKeBoard.setVisible(false);
            addDailyDisplayData();
        });
        ThongKeBtn.setOnAction(event -> {
            databoard.setVisible(false);
            ThuChiBoard.setVisible(false);
            thongKeBoard.setVisible(true);
            typeChange();
            accountChange();
        });
    }
}
