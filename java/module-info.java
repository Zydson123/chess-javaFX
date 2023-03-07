module com.example.chessjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.unsupported;


    opens com.example.chessjavafx to javafx.fxml;
    exports com.example.chessjavafx;
}