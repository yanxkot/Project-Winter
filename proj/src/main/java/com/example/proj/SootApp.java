package com.example.proj;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class SootApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(500);
        gameSettings.setHeight(300);
        gameSettings.setTitle("Soot(sin)");
    }
    @Override
    protected void initInput(){

       FXGL.onKey(KeyCode.RIGHT, "right",()->getPlayer().getComponent(PlayerControl.class).right());
       FXGL.onKey(KeyCode.LEFT, "left",()->getPlayer().getComponent(PlayerControl.class).left());



    }
    @Override
    protected void initGame(){
        /*
        FXGL.getGameWorld().addEntityFactory(new SootFactory());
        initLevel()
*/
    }
    private void initLevel(){
        /*
        //copied as example:
        FXGL.spawn("Background", new SpawnData(0, 0).put("width", 500).put("height", 300));
        FXGL.setLevelFromMap("level" + FXGL.geti("level") + ".tmx");

         */
    }
    @Override
    protected void initPhysics(){
        PhysicsWorld physics = FXGL.getPhysicsWorld();
        physics.setGravity(0,0);//idk how it works
        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER,SootType.PLATFORM) {
            @Override
            protected void onCollision(Entity player, Entity platform) {
                player.setY(platform.getY());
            }
        });

    }

    @Override
    protected void initUI(){
        Button button = new Button("Hi");
        button.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(button);
        vBox.setAlignment(Pos.CENTER);
        FXGL.getGameScene().addUINode(vBox);

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
        return FXGL.getGameWorld().getSingleton(SootType.PLAYER);
    }
    public static void main(String[] args) {
        launch(args);
    }
}