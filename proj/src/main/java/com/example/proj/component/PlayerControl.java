package com.example.proj.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.entity.components.TransformComponent;
import javafx.scene.input.KeyCode;

import java.security.Key;

public class PlayerControl extends Component {
    private PhysicsComponent physics;
    private TransformComponent position;
    private double speed;
    int jumps = 1;

    /**
     * this method determines the increment when player presses left
     */

    public void left(){
        if(physics.isOnGround()) {
            physics.setVelocityX(-50);
        }
        //else if(!physics.isOnGround()) physics.setVelocityX(0);
    }

    /**
     * this method determines the increment when player presses right
     */
    public void right(){
        if(physics.isOnGround()) {
        physics.setVelocityX(50);
        }
        //else if(!physics.isOnGround()) physics.setVelocityX(0);
    }

    /**
     * this method determines the increment when player presses up
     */

    public void jump(){
        if (physics.isOnGround()) {
               jumps = 1;
            //physics.setVelocityY(0);
            //physics.setVelocityX(0);
            }
        if(jumps == 1) {
            physics.setVelocityY(-300);
            //physics.setVelocityX(100);
            jumps--;
        }
        else if(physics.isOnGround() && jumps == 0){
            physics.setVelocityY(0);
            physics.setVelocityX(0);
        }
    }

    public void stop(){
        physics.setVelocityX(0);
    }

    @Override
    public void onUpdate(double tpf){
        //speed = tpf * 60;
    }

}
