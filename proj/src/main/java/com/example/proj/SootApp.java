package com.example.proj;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.example.proj.component.PlayerControl;
import com.almasb.fxgl.input.UserAction;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;
import static com.example.proj.SootType.PLATFORM;

//TODO: add randomization method to generate levels.
public class SootApp extends GameApplication {
    private Stage popupStage;

    private boolean doorCompletion = false;
    int level;
    int life;
    int doorAnswer;
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
        //TODO: modify dimensions of screen or create custom dialog factory service
        //width=700 to allow enough space for error message, (originally 450)
        //temporary resolution
        gameSettings.setWidth(800);
        gameSettings.setHeight(360);
        gameSettings.setTitle("Soot(sin)");

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
     * This method initializes a level in the game by setting a map per each level.
     * The character is also initialized in this method.
     */
    protected void initGame() {
        getGameWorld().addEntityFactory(new SootFactory());
        player = null;
        initLevel();
        player = spawn("Player", 50, 50);
        player.addComponent(new PlayerControl());
        //getGameWorld().spawn("platform", 50, 50);
        set("Player", player);
        //life count display
        HBox lifeView = new HBox();
        Text lifeCount = getUIFactoryService().newText("");
        lifeCount.textProperty().bind(getWorldProperties().intProperty("life").asString());
        lifeCount.setFill(Color.BLACK);
        lifeView.getChildren().addAll(getAssetLoader().loadTexture("heart.png"),lifeCount);
        lifeView.setTranslateX(60);
        lifeView.setTranslateY(10);
        getGameScene().addUINodes(lifeView);



        //getGameWorld().setLevelFromMap("1plat.tmx");
        //getGameWorld().setLevel(level);
    }

    /**
     * This method initializes the game variables such as life, level, .... etc
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", 1);
        vars.put("life", 5);
    }
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
            protected void onCollisionBegin(Entity player, Entity platform) {
                player.getComponent(PlayerControl.class).stop();


            }
        });

        physics.addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.DANGER) {
            @Override
            protected void onCollision(Entity player, Entity danger) {
                player.getComponent(PlayerControl.class).die();
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


        toolBar = new VBox();
        Text tool = new Text("tools");
        Button jumpB = new Button("Jump");
        jumpB.setFocusTraversable(false);
        jumpB.setOnAction(event -> {

            GridPane properties = new GridPane();
            Text velocityText = new Text("Velocity");
            velocityText.setFill(Color.WHITE);
            TextField velocity = new TextField("Velocity");
            Text angleText = new Text("Angle");
            angleText.setFill(Color.WHITE);
            TextField angle = new TextField("Angle");
            //TODO: add input limitations(range)
            addNumericInputValidation(velocity);
            addNumericInputValidation(angle);
            Button exit = getUIFactoryService().newButton("x");
            Button jumpAction = new Button("jump");
            jumpAction.setOnAction(event1 ->{
                player.getComponent(PlayerControl.class).jumpT(Double.parseDouble(velocity.getText()),Double.parseDouble(angle.getText()));
                getDialogService().onExit();
            });
            properties.addRow(0, velocityText, velocity);
            properties.addRow(1, angleText,angle);
            properties.addRow(2, jumpAction);
            properties.setHgap(15);
            getDialogService().showBox("Edit Jump Properties", properties,jumpAction, exit);

        });

        toolBar.getChildren().addAll(tool,jumpB);
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


        VBox popupContent = new VBox();
        TextField answerField = new TextField();



        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            popupStage.close();
        });

        Button checkButton = new Button("check answer");
        checkButton.setOnAction(event -> {
            doorAnswer = Integer.parseInt(answerField.getText());
            verifyAnswer();
        });

        Question.derivative();
        Label questionDoor = new Label(Question.getPremise());
        popupContent.getChildren().addAll(questionDoor, answerField, checkButton, closeButton);
        popupContent.setAlignment(Pos.CENTER);


        popupContent.setPrefSize(300, 200);


        Scene popupScene = new Scene(popupContent);


        popupStage.setScene(popupScene);


        popupStage.show();
    }

    //TODO: add if and else statement to check validity of answer
    private void verifyAnswer() {
        if(doorAnswer == Question.getAnswer()){
            Label win = new Label("Congrats! Right Answer!");
            Scene sceneCongrats = new Scene(win);
            popupStage.setScene(sceneCongrats);
        }
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

    /**
     * This method displays an error message if the user's input is not numeric
     *
     */
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