package com.example.proj;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.example.proj.component.PlayerControl;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.example.proj.SootType.PLATFORM;

public class SootApp extends GameApplication {
    private Stage popupStage;

    private boolean doorCompletion = false;
    int level;
    int life;
    private Entity player;
    private VBox toolBar;
    //private Alert invalidInputError;
    //Stage taskStage = new Stage();
    //Scene taskScene;


    @Override()
    /**
     * @param gameSettings this is the main window of the game
     * This method initializes the width, height and title of the game.
     */
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(450);
        gameSettings.setHeight(360);
        gameSettings.setTitle("Soot(sin)");
        //toolbar


    }

    @Override
    /**
     * This method initialise the controls of the main character. It assigns a key to a movement
     */
    protected void initInput() {
        getInput().addAction(new UserAction("left") {
            @Override
            protected void onAction() {
                
                player.getComponent(PlayerControl.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerControl.class).stop();
            }
        }, KeyCode.LEFT);

        getInput().addAction(new UserAction("right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControl.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerControl.class).stop();
            }
        }, KeyCode.RIGHT);

        getInput().addAction(new UserAction("jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerControl.class).jump();
            }

            /*@Override
            protected void onActionEnd() {
                player.getComponent(PlayerControl.class).stop();
            }*/
        }, KeyCode.UP);

        //getInput().addAction(new UserAction("click") {
        //}, );
    }

    @Override
    /**
     * This method initializes a level in the game by setting a map per each level. The character is also initialized in this method.
     */
    protected void initGame() {
        getGameWorld().addEntityFactory(new SootFactory());
        player = null;
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
     */
    private void initLevel() {
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
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        //one(1) meter ≈ 38 pixels
        //9.81m/s^2 ≈ 372.78 pixels
        physics.setGravity(0, 372.78);

        //TODO: door, not platform
        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, PLATFORM) {
            @Override
            protected void onCollision(Entity player, Entity platform) {



            }
        });

        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.DANGER) {
            @Override
            protected void onCollisionBegin(Entity player, Entity danger) {
                life--;
            }
        });

        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                //to verify collision
                if (doorCompletion == false)
                    popUp();
                System.out.println("door");
                getGameScene().removeUINode(toolBar);

            }
        });
        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.OBSTACLE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity obstacle) {
                //to verify collision
                System.out.println("obstacle");

            }
        });
    }


    /**
     * This method imports a gif file to then initialize to the character.
     */
    @Override
    protected void initUI() {

        /*
        VBox content = new VBox(
                getUIFactoryService().newText("Line 1"),
                getUIFactoryService().newText("Line 2"),
                getAssetLoader().loadTexture("brick.png"),
                getUIFactoryService().newText("Line 3"),
                getUIFactoryService().newText("Line 4")
        );

        Button btnClose = getUIFactoryService().newButton("Press me to close");
        btnClose.setPrefWidth(300);

        getDialogService().showBox("This is a customizable box", content, btnClose);
        */
        toolBar = new VBox();
        Button jumpB = new Button("Jump");
        jumpB.setDefaultButton(false);
        jumpB.setOnAction(event -> {

            GridPane properties = new GridPane();
            Text editProp = new Text("Edit Jump Properties");
            Text velocityText = new Text("Velocity");
            TextField velocity = new TextField("Velocity");
            Text angleText = new Text("Angle");
            TextField angle = new TextField("Angle");
<<<<<<< Updated upstream
            Button exit = new Button("x");
            properties.getChildren().addAll(velocity, angle);
            editJ.getChildren().addAll(properties, exit);
            editJ.setTopAnchor(exit, 2.);
            editJ.setRightAnchor(exit, 2.);
            getGameScene().addUINode(editJ);
            exit.setOnAction(event1 -> {
                getGameScene().removeUINode(editJ);
            });
        });
=======
            velocity.setText("10");
            angle.setText("45");
            addNumericInputValidation(velocity);
            addNumericInputValidation(angle);
            Button exit = getUIFactoryService().newButton("x");;
            properties.addRow(0, editProp);
            properties.addRow(1, velocityText, velocity);
            properties.addRow(2, angleText,angle);
            getDialogService().showBox("Edit Jump Properties", properties, exit);
            });
>>>>>>> Stashed changes
        toolBar.getChildren().add(jumpB);
        toolBar.setTranslateX(10);
        toolBar.setTranslateY(10);
        getGameScene().addUINode(toolBar);



        // */
    }


    /**
     * This method opens a Stage
     */


    private void popUp() {

        if (popupStage != null && popupStage.isShowing()) {
            return;
        }


        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Popup");


        StackPane popupContent = new StackPane();


        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {

            popupStage.close();
        });

        Button checkButton = new Button("check answer");
        checkButton.setOnAction(event -> {
           verifyAnswer();

        });
        popupContent.getChildren().addAll(closeButton, checkButton);


        popupContent.setPrefSize(300, 200);


        Scene popupScene = new Scene(popupContent);


        popupStage.setScene(popupScene);


        popupStage.show();
    }

    private void verifyAnswer() {
        //if answer is right then doorCompletion is true
        doorCompletion = true;
    }

    /**
     * This method runs the application
     *
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
     *
     * @return
     */
    private static Entity getPlayer() {
        return getGameWorld().getSingleton(SootType.PLAYER);
    }
    private void addNumericInputValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*\\.?\\d*|-")) {
                String input = textField.getText();
                try {
                    double value = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    getDialogService().showErrorBox("Input must be a valid number. Please try again.", () -> {
                        textField.setText(oldValue);

                    });

                }
            }

        });
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


}