package com.example.proj;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.example.proj.component.PlayerControl;


public class SootFactory implements EntityFactory {
    @Spawns("Platform")

    /**
     * This method spawns an entity of type Platform
     */
    public Entity newPlatform(SpawnData data){
    return FXGL.entityBuilder(data)
            .from(data)
            .type(SootType.PLATFORM)
            .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
            .with(new PhysicsComponent())
            .collidable()
            .build();
    }
    @Spawns("Player")
    public Entity newPlayer(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        return FXGL.entityBuilder(data)
                .type(SootType.PLAYER)
                .view("finalSOOT.gif")
                .bbox(new HitBox(BoundingShape.box(20,20)))
                .with(physics)
                .with(new PlayerControl())
                .with(new CollidableComponent(true))
                .build();
    }

    public void newObstacle(Spawns data){

    }
    public void newDoor(Spawns data){

    }
    public void newLevel(Spawns data){

    }

}
