package com.example.proj.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.entity.components.TransformComponent;
public class PlayerControl extends Component {
    private PhysicsComponent physics;
    private TransformComponent position;
    private double speed;

    private void left(){
        position.translateX(-5 * speed);
    }

    private void right(){
        position.translateX(5 * speed);
    }

    private void jump(){


    }


    @Override
    public void onUpdate(double tpf){
        speed = tpf * 60;
    }
}
