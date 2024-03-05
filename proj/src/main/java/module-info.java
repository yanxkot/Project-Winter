module com.example.proj {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.proj to javafx.fxml;
    exports com.example.proj;
}