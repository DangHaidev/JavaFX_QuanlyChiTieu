module javaproject.expense_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens javaproject.expense_management to javafx.fxml;
    exports javaproject.expense_management;
}