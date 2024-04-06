module javaproject.expense_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens javaproject.expense_management to javafx.fxml;
    exports javaproject.expense_management;
}