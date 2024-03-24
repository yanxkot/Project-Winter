package com.example.proj.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.entity.components.TransformComponent;
public class PlayerControl extends Component {
    private PhysicsComponent physics;
    private TransformComponent position;
    private double speed;
    int jumps = 1;

    /**
     * this method determines the increment when player presses left
     */

    public void left(){
        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (!isOnGround) {
                physics.setVelocityX(0);
            }
        });
        physics.setVelocityX(-50);
    }

    /**
     * this method determines the increment when player presses right
     */
    public void right(){
        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (!isOnGround) {
                physics.setVelocityX(0);
            }
        });
        physics.setVelocityX(50);
    }

    /**
     * this method determines the increment when player presses up
     */

    public void jump(){
        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = 1;
            }
        });
        if(jumps == 1) {
            physics.setVelocityY(-300);
            jumps--;
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
