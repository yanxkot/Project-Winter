package com.example.proj.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.entity.components.TransformComponent;
public class PlayerControl extends Component {
    private PhysicsComponent physics;
    private TransformComponent position;
    private double speed;

    /**
     * this method determines the increment when player presses left
     */
    public void left(){
        position.translateX(-5 * speed);
    }

    /**
     * this method determines the increment when player presses right
     */
    public void right(){
        position.translateX(5 * speed);
    }

    /**
     * this method determines the increment when player presses up
     */
    private void jump(){

    }

    @Override
    public void onUpdate(double tpf){
        speed = tpf * 60;
    }

}
