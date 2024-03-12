package com.example.proj;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.example.proj.component.PlayerControl;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class SootApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(500);
        gameSettings.setHeight(300);
        gameSettings.setTitle("Soot(sin)");
    }

    @Override
    protected void initInput(){

       onKey(KeyCode.RIGHT, "right",()->getPlayer().getComponent(PlayerControl.class).right());
       onKey(KeyCode.LEFT, "left",()->getPlayer().getComponent(PlayerControl.class).left());

    }

    @Override
    protected void initGame(){


        getGameWorld().addEntityFactory(new SootFactory());
        player=null;
        initLevel();
        player = spawn("Player", 50, 50);
        //getGameWorld().spawn("platform", 50, 50);

        set("Player", player);
        //getGameWorld().setLevelFromMap("1plat.tmx");
        //getGameWorld().setLevel(level);

       Level level = FXGL.setLevelFromMap("tmx/1plat.tmx");


       // Level level = setLevelFromMap("file:proj/main/java/resources/assets/tmx/1plat.tmx");


        //getGameWorld().setLevel(level);
      //  FXGL.setLevelFromMap("1plat.tmx");




    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", 1);
        vars.put("lives", 3);

    }

    //TODO: build2 @
    private void initLevel(){
        /*
        //copied as example:
        FXGL.spawn("Background", new SpawnData(0, 0).put("width", 500).put("height", 300));
        FXGL.setLevelFromMap("level" + FXGL.geti("level") + ".tmx");

         */
    }

    @Override
    protected void initPhysics(){
        PhysicsWorld physics = getPhysicsWorld();

        //one(1) meter ≈ 38 pixels
        //9.81m/s^2 ≈ 372.78 pixels
        physics.setGravity(0, 372.78);

        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.PLATFORM) {
            @Override
            protected void onCollision(Entity player, Entity platform) {

                player.setY(platform.getY());
            }


        });

        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.DANGER) {
            @Override
            protected void onCollision(Entity player, Entity danger) {

                FXGL.inc("lives", -1);
            }

        });

        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER,SootType.DOOR) {
            @Override
            protected void onCollision(Entity player, Entity door) {


            }

        });


    }

    @Override
    protected void initUI(){
        Image soot = new Image("file:proj/src/main/resources/Assets/Texture/sootyyy.gif");
        ImageView sootV = new ImageView(soot);


        Button button = new Button("Hi");
        button.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(button);
        vBox.setAlignment(Pos.CENTER);
        //getGameScene().addUINode(sootV);

    }
/*
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SootApp.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        System.out.println(1);
    }
*/
    private static Entity getPlayer(){
        return getGameWorld().getSingleton(SootType.PLAYER);
    }
    public static void main(String[] args) {
        launch(args);
    }
}