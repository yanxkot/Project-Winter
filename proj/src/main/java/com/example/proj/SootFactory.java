package com.example.proj;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.example.proj.component.PlayerControl;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import com.example.proj.SootType.*;

/**
 *
 */
public class SootFactory implements EntityFactory {


    /**
     * This method spawns an entity of type Platform
     */
    @Spawns("Platform")
    public Entity newPlatform(SpawnData data){
    return FXGL.entityBuilder(data)
            .type(SootType.PLATFORM)
            .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
            .with(new PhysicsComponent())
            .with(new CollidableComponent(true))
            .build();

    }

    /**
     * This method spawns an entity of type Player
     * @param data
     * @return
     */
    @Spawns("Player")
    public Entity newPlayer(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0f));
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(6, 8)));
        return FXGL.entityBuilder(data)
                .type(SootType.PLAYER)
                .view("finalSOOT.gif")
                .bbox(new HitBox(BoundingShape.box(20,20)))
                .with(physics)
                .with(new PlayerControl())
                .with(new CollidableComponent(true))
                .build();
    }

    /**
     * This method spawns an entity of type Obstacle
     * @param data
     */
    @Spawns("Obstacle")
    public Entity newObstacle(SpawnData data){
        return FXGL.entityBuilder(data)
                .from(data)
                .type(SootType.OBSTACLE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .collidable()
                .build();
    }

    /**
     * This method spawns an entity of type Door
     * @param data
     */
    @Spawns("Door")
    public Entity newDoor(SpawnData data){
        return FXGL.entityBuilder(data)
                .from(data)
                .type(SootType.DOOR)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .collidable()
                .build();

    }

    /**
     * This method spawns an entity of type Level
     * @param data
     */
    //TODO:Build2
    public void newLevel(Spawns data){

    }



}
