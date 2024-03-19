package com.example.proj;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class SootApp extends GameApplication {

    int level;
    int life;
    private Entity player;

    @Override
    /**
     * @param gameSettings this is the main window of the game
     * This method initializes the width, height and title of the game.
     */
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(500);
        gameSettings.setHeight(300);
        gameSettings.setTitle("Soot(sin)");

    }

    @Override
    /**
     * This method initialise the controls of the main character. It assigns a key to a movement
     */
    protected void initInput(){
       onKey(KeyCode.RIGHT, "right",()->getPlayer().getComponent(PlayerControl.class).right());
       onKey(KeyCode.LEFT, "left",()->getPlayer().getComponent(PlayerControl.class).left());
    }

    @Override
    /**
     * This method initializes a level in the game by setting a map per each level. The character is also initialized in this method.
     */
    protected void initGame(){
        getGameWorld().addEntityFactory(new SootFactory());
        player=null;
        initLevel();
        player = spawn("Player", 50, 50);
        player.addComponent(new PlayerControl());
        //getGameWorld().spawn("platform", 50, 50);
        set("Player", player);
        //getGameWorld().setLevelFromMap("1plat.tmx");
        //getGameWorld().setLevel(level);


    }

    /**
     * This method initializes the game variables such as life, level, .... etc
     */
    protected void initGameVars() {
        level = 1;
        life = 3;
    }

    //TODO: build2 @
    /**
     * this method imports the corresponding tmx file from the resources file to the correct level.
     *
     */
    private void initLevel(){
        Level level = FXGL.setLevelFromMap("tmx/Plat1.9.0.tmx");
        /*
        //copied as example:
        FXGL.spawn("Background", new SpawnData(0, 0).put("width", 500).put("height", 300));
        FXGL.setLevelFromMap("level" + FXGL.geti("level") + ".tmx");
         */
    }

    @Override
    /**
     * This method initializes the game's physics such as gravity. It also handles collision between two entities.
     */
    protected void initPhysics(){
        PhysicsWorld physics = getPhysicsWorld();
        //one(1) meter ≈ 38 pixels
        //9.81m/s^2 ≈ 372.78 pixels
        physics.setGravity(0, 372.78);

        //TODO: door, not platform
        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.PLATFORM) {
            @Override
            protected void onCollision(Entity player, Entity platform) {

                player.setY(platform.getY());
                popUp();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.DANGER) {
            @Override
            protected void onCollision(Entity player, Entity danger) {
                life--;
            }
        });

        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.DOOR) {
            @Override
            protected void onCollision(Entity player, Entity door) {
                popUp();
            }
        });
    }

    @Override
    /**
     * This method imports a gif file to then initialize to the character.
     */
    protected void initUI(){
        Image soot = new Image("file:proj/src/main/resources/Assets/Texture/sootyyy.gif");
        ImageView sootV = new ImageView(soot);
        Button button = new Button("Hi");
        button.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(button);
        vBox.setAlignment(Pos.CENTER);
        //getGameScene().addUINode(sootV);
    }

    /**
     * This method opens a Stage
     */
    protected void popUp(){
        Stage taskStage = new Stage();
        taskStage.initModality(Modality.APPLICATION_MODAL);
        taskStage.setTitle("door");

        StackPane taskLayout = new StackPane();
        taskLayout.getChildren().add(new Button("done"));

        Scene taskScene = new Scene(taskLayout, 250, 150);
        taskStage.setScene(taskScene);

        taskStage.showAndWait();
    }

    /**
     * This method runs the application
     * @param stage
     * @throws IOException
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SootApp.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        System.out.println(1);
    }

    /**
     * This method returns the player
     * @return
     */
    private static Entity getPlayer(){
        return getGameWorld().getSingleton(SootType.PLAYER);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


}