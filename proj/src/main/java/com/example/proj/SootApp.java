package com.example.proj;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SootApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(500);
        gameSettings.setHeight(300);
        gameSettings.setTitle("Soot(sin)");
    }
    protected void initInput(){
        /*
        Input input = FXGL.getInput();
        input.addAction(new UserAction("right"){
            @Override
            protected void onAction(){

            }
        })

         */

    }
    protected void initGame(){

    }
    protected void initPhysics(){

    }

    protected void initUI(){
        Button button = new Button("Hi");
        button.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(button);
        vBox.setAlignment(Pos.CENTER);
        FXGL.getGameScene().addUINode(vBox);

    }

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SootApp.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        System.out.println(1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}