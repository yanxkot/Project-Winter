package com.example.proj;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.example.proj.component.PlayerControl;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
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
import javafx.geometry.Point2D;


import java.io.IOException;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;
import static com.example.proj.SootType.PLATFORM;
import static com.example.proj.SootType.PLAYER;

//TODO: add randomization method to generate levels.
public class SootApp extends GameApplication {

    private Stage popupStage;
    private boolean doorCompletion = false;
    //int level;
    //int life;
    private static final int MAX_LEVEL=3;
    private static final int FIRST_LEVEL=0;
    int doorAnswer;
    private Entity player;
    private VBox toolBar;
    final String RIGHT = "right";
    final String LEFT = "left";
    final String JUMP = "jump";
    /*
    public Entity getPlayer() {
        return getGameWorld().getSingleton(SootType.PLAYER);
    }
    public PlayerControl getPlayerControl(){
        return getPlayer().getComponent(PlayerControl.class);
    }

    @Override()
    /**
     * @param gameSettings this is the main window of the game
     * This method initializes the width, height and title of the game.
     */
    @Override
    protected void initSettings(GameSettings gameSettings) {
        //TODO: modify dimensions of screen or create custom dialog factory service
        //width=700 to allow enough space for error message, (originally 450)
        //temporary resolution
        gameSettings.setWidth(800);
        gameSettings.setHeight(360);
        gameSettings.setTitle("Soot(sin)");
    }


    /**
     * This method initialise the controls of the main character. It assigns a key to a movement
     */
    @Override
    protected void initInput() {
        getInput().addAction(new UserAction(LEFT) {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControl.class).left();
                //player.getComponent(PlayerControl.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerControl.class).stop();
                //player.getComponent(PlayerControl.class).stop();
            }
        }, KeyCode.LEFT);

        getInput().addAction(new UserAction(RIGHT) {
            @Override
            protected void onAction() {
                //getPlayerControl().right();
                player.getComponent(PlayerControl.class).right();
            }

            @Override
            protected void onActionEnd() {
                //getPlayerControl().stop();
                player.getComponent(PlayerControl.class).stop();
            }
        }, KeyCode.RIGHT);

        getInput().addAction(new UserAction(JUMP) {
            @Override
            protected void onActionBegin() {
                //getPlayerControl().jump();
                player.getComponent(PlayerControl.class).jump();
            }

            /*@Override
            protected void onActionEnd() {
                player.getComponent(PlayerControl.class).stop();
            }*/
        }, KeyCode.UP);
    }
    /**
     * This method initializes the game variables such as life, level, .... etc
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", FIRST_LEVEL);
        vars.put("life", 5);
        vars.put("levelTime",0.0);
    }
    @Override
    /**
     * This method initializes a level in the game by setting a map per each level.
     * The character is also initialized in this method.
     */
    protected void initGame() {
        getGameWorld().addEntityFactory(new SootFactory());


        player = null;
        nextLevel();
        player = spawn("Player", 50, 50);
        //player.addComponent(gPlayerControl);
        set("Player", player);



        //getGameWorld().spawn("platform", 50, 50);

        //life count display
        HBox lifeView = new HBox();
        Text lifeCount = getUIFactoryService().newText("");
        lifeCount.textProperty().bind(getWorldProperties().intProperty("life").asString());
        lifeCount.setFill(Color.BLACK);
        lifeView.getChildren().addAll(getAssetLoader().loadTexture("heart.png"), lifeCount);
        lifeView.setTranslateX(60);
        lifeView.setTranslateY(10);
        getGameScene().addUINodes(lifeView);

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, 0, 250 * 70, getAppHeight());
        viewport.bindToEntity(player, 50, getAppHeight() / 2);
        viewport.setLazy(true);


        //getGameWorld().setLevelFromMap("1plat.tmx");
        //getGameWorld().setLevel(level);
    }



/*
    protected void initGameVars() {
        level = 1;
        life = 3;
    }
*/
    //TODO: build2 @




    /**
     * This method initializes the game's physics such as gravity. It also handles collision between two entities.
     */
    @Override
    protected void initPhysics() {
        //PhysicsWorld physics = getPhysicsWorld();
        //one(1) meter ≈ 38 pixels
        //9.81m/s^2 ≈ 372.78 pixels
        getPhysicsWorld().setGravity(0, 372.78);


        //TODO: door, not platform
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(SootType.PLAYER, PLATFORM) {
            @Override
            protected void onCollisionBegin(Entity player, Entity platform) {
                player.getComponent(PlayerControl.class).stop();

            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.DANGER) {
            @Override
            protected void onCollision(Entity player, Entity danger) {
                player.getComponent(PlayerControl.class).die();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.DOOR) {

            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                //to verify collision
                if (doorCompletion == false)
                    popUp();
                System.out.println("door");

            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(SootType.PLAYER, SootType.OBSTACLE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity obstacle) {
                //to verify collision
                System.out.println("obstacle");
            }
        });
    }
    @Override
    protected void onUpdate(double tpf){
        inc("levelTime",tpf);
        if(player.getY()>getAppHeight()){
            initLevel(geti("level"));
        }
    }
    private void nextLevel(){
        if(geti("level")==MAX_LEVEL){
            getGameScene().removeUINode(toolBar);
            showMessage("that's all we've got");
            return;
        }
        inc("level",1);
        initLevel(geti("level"));
    }
    /**
     * this method imports the corresponding tmx file from the resources file to the correct level.
     */



    private void initLevel(int levelNumb) {

        if (player != null) {

            player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
            player.setZIndex(Integer.MAX_VALUE);
        }
        set("levelTime",0.0);
            /*
            getGameWorld().getSingleton(SootType.PLAYER).getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));

            //player.addComponent(new PlayerControl());
            player.setZIndex(Integer.MAX_VALUE);
            //getGameScene().getViewport().bindToEntity(player, getAppWidth()/2 , getAppHeight() / 2);
        }
*/
        //String lev = String.format("%d", getWorldProperties().getInt("level"));
        Level level = FXGL.setLevelFromMap("tmx/Plat" + levelNumb + "Temp.tmx");




    }

    /**
     * This method imports a gif file to then initialize to the character.
     */
    @Override
    protected void initUI() {
        toolBar = new VBox();
        Text tool = new Text("Tools");
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
            jumpAction.setOnAction(event1 -> {
                player.getComponent(PlayerControl.class).jumpT(Double.parseDouble(velocity.getText()), Double.parseDouble(angle.getText()));
                getDialogService().onExit();
            });

            properties.addRow(0, velocityText, velocity);
            properties.addRow(1, angleText, angle);
            properties.addRow(2, jumpAction);
            properties.setHgap(15);
            getDialogService().showBox("Edit Jump Properties", properties, jumpAction, exit);
        });

        toolBar.getChildren().addAll(tool, jumpB);
        toolBar.setTranslateX(10);
        toolBar.setTranslateY(10);
        getGameScene().addUINode(toolBar);
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
        popupStage.setTitle("Pop-up");

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
        if (doorAnswer == Question.getAnswer()) {
            Label win = new Label("Congrats! Right Answer!");
            Scene sceneCongrats = new Scene(win);
            popupStage.setScene(sceneCongrats);
            getGameScene().getViewport().fade(() -> {
                nextLevel();
                //getGameController().startNewGame();
            });
            //initLevel();
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


    /**
     * This method displays an error message if the user's input is not numeric
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