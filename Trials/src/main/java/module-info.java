module com.example.trials {
    requires javafx.controls;
    requires javafx.fxml;
            
                                requires com.almasb.fxgl.all;
    
    opens com.example.trials to javafx.fxml;
    exports com.example.trials;
}