module com.example.chessjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chessjavafx to javafx.fxml;
    exports com.example.chessjavafx;
}