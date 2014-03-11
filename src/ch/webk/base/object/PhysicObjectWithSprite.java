package ch.webk.base.object;

import java.util.ArrayList;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import ch.webk.base.Logger;
import ch.webk.base.factory.CustomPhysicsFactory;
import ch.webk.base.manager.ManagerAnimation;
import ch.webk.base.manager.ManagerObject;
import ch.webk.base.manager.system.ManagerResources;
import ch.webk.base.manager.system.ManagerVertices;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class PhysicObjectWithSprite implements IPhysicObject {

	private static final String TAG = "PhysicObjectWithSprite";
	
	private float livingPoints = Float.POSITIVE_INFINITY;
	private float maxLivingPoints = Float.POSITIVE_INFINITY;
	private AnimatedSprite animatedSpriteLivingPoints;
	private boolean shouldDie = false;
	
	private Body body;
	private BodyType bodyType;
	private FixtureDef fixtureDef;
	private String poly;
	private Sprite sprite; 
	ITextureRegion region;
	
	public PhysicObjectWithSprite(float x, float y, FixtureDef fixtureDef, BodyType bodyType, String poly, ITextureRegion region, float sx, float sy) {
		Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite");
		this.poly = poly;
	    this.fixtureDef = fixtureDef;
	    this.bodyType = bodyType;
	    Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite1");
	    createSprite(region, x, y, sx, sy);
	    Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite2");
	    createPhysics(bodyType);
	    //getBody().setLinearDamping(0.1f);
	    //getBody().setAngularDamping(0.2f);
	    Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite3");
	    createLivingPoints();
	    Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite4");
	}
	
	public PhysicObjectWithSprite(float x, float y, FixtureDef fixtureDef, BodyType bodyType, String poly, ITiledTextureRegion region, float sx, float sy) {
		Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite");
		this.poly = poly;
	    this.fixtureDef = fixtureDef;
	    this.bodyType = bodyType;
	    Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite1");
	    createAnimatedSprite(region, x, y, sx, sy);
	    Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite2");
	    createPhysics(bodyType);
	    Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite3");
	    //getBody().setLinearDamping(0.1f);
	    //getBody().setAngularDamping(0.2f);
	    Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite4");
	    createLivingPoints();
	    Logger.i("PhysicObjectWithSprite", "PhysicObjectWithSprite5");
	}
	
	private void createPhysics(BodyType bodyType) {
    	if (poly.equals("circle")) {
    		body = CustomPhysicsFactory.createCircleBody(ManagerObject.getPhysicsWorld(), sprite, bodyType, fixtureDef);
    	} else if (poly.equals("box")) {
    		body = CustomPhysicsFactory.createBoxBody(ManagerObject.getPhysicsWorld(), sprite, bodyType, fixtureDef);
    	} else {
            body = CustomPhysicsFactory.createComplexBody(ManagerObject.getPhysicsWorld(), sprite, bodyType, fixtureDef, ManagerVertices.getVertices(region.toString(), poly, sprite));
    	}
        body.setUserData(this);
        
        ManagerObject.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true) {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
                if (shouldDie) {shouldDie=false; die(); }
                onPerformUpdate();
            }
        });
    }
	
	private void createSprite(ITextureRegion region, float x, float y, float sx, float sy) {
		this.region = region;
		sprite = new Sprite(x, y, region, ManagerResources.getInstance().getVertexBufferObjectManager());
	    sprite.setScale(sx, sy);
	    ManagerObject.getGameScene().attachChild(sprite);
	}
	
	private void createAnimatedSprite(ITiledTextureRegion region, float x, float y, float sx, float sy) {
		this.region = region;
		sprite = new AnimatedSprite(x, y, region, ManagerResources.getInstance().getVertexBufferObjectManager());
		sprite.setScale(sx, sy);
	    ManagerObject.getGameScene().attachChild(sprite);
	}
	
	protected void createLivingPoints() {
		animatedSpriteLivingPoints = ManagerObject.getAnimatedSprite(getSprite().getX(), getSprite().getY(), "livingpoints");
		animatedSpriteLivingPoints.setOffsetCenterY(2.5f);
		animatedSpriteLivingPoints.setScale(0.8f);
		ManagerObject.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(animatedSpriteLivingPoints, getBody(), true, false));
		ManagerObject.getGameScene().attachChild(animatedSpriteLivingPoints);
	}
    
    public Body getBody() {
    	return body;
    }
    
    public BodyType getBodyType() {
    	return bodyType;
    }
    
    public Sprite getSprite() {
    	return sprite;
    }
    
    public AnimatedSprite getAnimatedSprite() {
    	return (AnimatedSprite) sprite;
    }
    
    public void setLivingPoints(float value) {
		livingPoints = Float.valueOf(value);
	}    
    public float getLivingPoints() {
		return livingPoints;
	}
    
    public void initLivingPoints(float value) {
    	livingPoints = Float.valueOf(value);
		maxLivingPoints = Float.valueOf(value);
	}    
    public float getMaxLivingPoints() {
		return maxLivingPoints;
	}
    
    public AnimatedSprite getAnimatedLivingPoints() {
    	return animatedSpriteLivingPoints;
    }
    public void setAnimatedLivingPoints(AnimatedSprite animatedSpriteLivingPoints) {
    	this.animatedSpriteLivingPoints = animatedSpriteLivingPoints;
    }
    
    @Override
    public void damageReceived(float damage, Vector2 direction) {
    	if (damage > 0) {
    		livingPoints -= (damage / getBody().getFixtureList().size());
    		if (livingPoints < 0) {
    			livingPoints = 0;
    		}
    		if (getLivingPoints() != Float.POSITIVE_INFINITY) {
	    		int currentFrame = 10 - Math.round(getLivingPoints() / getMaxLivingPoints() * 10);
	    		ManagerAnimation.stepToFrame(animatedSpriteLivingPoints, currentFrame);
	    		if (livingPoints <= 0) {
	    			shouldDie = true;
	    		}
    		}
    	}
    }
    
    @Override
    public void beginContact(Body body) { }
        
    @Override
    public void endContact(Body body) { }
   
    public void onPerformUpdate() { }
    
    public void disposeRest(ArrayList<Sprite> spriteArray) {
    	spriteArray.add(animatedSpriteLivingPoints);
    	ManagerObject.removeFromGameScene(getBody(), spriteArray);
    }
    
    public abstract void die();
    
}