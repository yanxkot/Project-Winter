package com.example.proj.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.example.proj.SootApp;
import com.example.proj.SootType;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.security.Key;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayerControl extends Component {
    private PhysicsComponent physics;
    private TransformComponent position;
    private double speed;
    private int jumps = 1;
    //meters to pixels conversion ratio
    int mtp = 38;
    @Override
    public void onAdded(){
        //this listener resets the number of jumps
        physics.onGroundProperty().addListener((obs,old, isOnGround)->{
            if(isOnGround){
                jumps=1;
            }
        });
    }

    /**
     * this method determines the increment when player presses left
     */

    public void left() {
        //if (physics.isOnGround()) {
            physics.setVelocityX(-50);
        //}
        //else if(!physics.isOnGround()) physics.setVelocityX(0);
    }

    /**
     * this method determines the increment when player presses right
     */
    public void right() {
        //if (physics.isOnGround()) {
            physics.setVelocityX(50);
        //}
        //else if(!physics.isOnGround()) physics.setVelocityX(0);
    }

    /**
     * this method determines the increment when player presses up
     */

    public void jump() {
        if (jumps == 0)
            return;
        physics.setVelocityY(-300);
        jumps--;
    }
    /*
        if (physics.isOnGround()) {
            jumps = 1;
            //physics.setVelocityY(0);
            //physics.setVelocityX(0);
        }
        if (jumps == 1) {
            physics.setVelocityY(-300);
            //physics.setVelocityX(100);
            jumps--;
        } else if (physics.isOnGround() && jumps == 0) {
            physics.setVelocityY(0);
            physics.setVelocityX(0);
        }
    }
    */


    public void jumpT(double velocity, double angle) {
        physics.setVelocityX(mtp * velocity * Math.cos(angle));
        physics.setVelocityY(-mtp * velocity * Math.sin(angle));
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    /**
     * this method removes a life and displays a game over message when 0
     */
    public void die() {
        inc("life", -1);

        if (geti("life") <= 0) {
            getDialogService().showMessageBox("Game Over",
                    () -> getGameController().startNewGame());
            return;
        }
        physics.overwritePosition(new Point2D(50, 50));
        physics.setVelocityX(0);
        physics.setVelocityY(0);
    }

    /**
     * this method checks if the player is within bounds
     */
    private void checkForBounds() {
        if (entity.getX() < 0||entity.getX() >= getAppWidth()||entity.getY() < 0||entity.getY() >= getAppHeight()) {
            die();
        }

    }

    @Override
    public void onUpdate(double tpf) {
        checkForBounds();

    }

}
