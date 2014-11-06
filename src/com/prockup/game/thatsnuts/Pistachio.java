package com.prockup.game.thatsnuts;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract class Pistachio extends Sprite {

	private Body body;
	private boolean cracked;
	
	public Pistachio(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
    {
        super(pX, pY, ResourcesManager.getInstance().pistachio_region, vbo);
        
        // Setup the physics environment
        createPhysics(camera, physicsWorld);
        
        // Initialized to uncracked of course
        cracked = false;
    }
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

	    body.setUserData("player");
	    body.setFixedRotation(true);
	    
	    physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
	    {
	        @Override
	        public void onUpdate(float pSecondsElapsed)
	        {
	            super.onUpdate(pSecondsElapsed);
	            camera.onUpdate(0.1f);
	            
	            if (getY() <= 0)
	            {                    
	                onDie();
	            }

	            body.setLinearVelocity(new Vector2(5, body.getLinearVelocity().y)); 
	        }
	    });
	}
	
	public void setCracked() {
		cracked = true;
		
		//http://www.matim-dev.com/full-game-tutorial---part-12.html
		//final long[] PLAYER_ANIMATE = new long[] { 100, 100, 100 };
        //
	    //animate(PLAYER_ANIMATE, 0, 2, true);
	}
	
	public void setFalling() {
		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, 12));
	}
	
	// Must be implemented during construction
	public abstract void onDie();
	
}
