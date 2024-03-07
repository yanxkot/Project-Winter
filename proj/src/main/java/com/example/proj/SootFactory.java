package com.example.proj;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;

public class SootFactory implements EntityFactory {
    @Spawns("Platform")
    public Entity newPlatform(SpawnData data){
    return FXGL.entityBuilder(data)
            .type(SootType.PLATFORM)
            .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
            .with(new PhysicsComponent())
            .collidable()
            .build();
    }
    @Spawns("Player")
    public Entity newPlayer(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(SootType.PLAYER)
                .view("file:proj/src/main/resources/Assets/Texture/sootyyy.gif")
                .bbox(new HitBox(BoundingShape.box(20,20)))
                .collidable()
                .build();

    }

    public void newObstacle(Spawns data){

    }
    public void newDoor(Spawns data){

    }
    public void newLevel(Spawns data){

    }

}
