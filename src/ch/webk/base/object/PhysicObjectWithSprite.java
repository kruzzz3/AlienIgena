package ch.webk.base.object;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import ch.webk.base.Logger;
import ch.webk.base.ObjectManager;
import ch.webk.base.ResourcesManager;
import ch.webk.base.VerticesManager;
import ch.webk.custom.factories.CustomPhysicsFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class PhysicObjectWithSprite implements IPhysicObject {

	private static final String TAG = "PhysicObjectWithSprite";
	
	private float livingPoints = Float.POSITIVE_INFINITY;
	private boolean shouldDie = false;
	
	private Body body;
	private BodyType bodyType;
	private FixtureDef fixtureDef;
	private String poly;
	private final Sprite sprite; 
	private ITextureRegion region;
	
	public PhysicObjectWithSprite(float x, float y, FixtureDef fixtureDef, BodyType bodyType, String poly, ITextureRegion  region, float sx, float sy) {
	    this.poly = poly;
	    this.fixtureDef = fixtureDef;
	    this.bodyType = bodyType;
	    this.region = region;
	    sprite = new Sprite(x, y, region, ResourcesManager.getInstance().getVertexBufferObjectManager());
	    sprite.setScale(sx, sy);
	    createPhysics(bodyType);
	    getBody().setLinearDamping(0.1f);
	    getBody().setAngularDamping(0.1f);
	}
	
	public PhysicObjectWithSprite(float x, float y, FixtureDef fixtureDef, BodyType bodyType, String poly, ITiledTextureRegion  region, float sx, float sy) {
	    this.poly = poly;
	    this.fixtureDef = fixtureDef;
	    this.bodyType = bodyType;
	    this.region = region;
	    sprite = new AnimatedSprite(x, y, region, ResourcesManager.getInstance().getVertexBufferObjectManager());
		sprite.setScale(sx, sy);
	    createPhysics(bodyType);
	}
	
	private void createPhysics(BodyType bodyType) {
    	if (poly.equals("circle")) {
    		body = CustomPhysicsFactory.createCircleBody(ObjectManager.getPhysicsWorld(), sprite, bodyType, fixtureDef);
    	} else if (poly.equals("box")) {
    		body = CustomPhysicsFactory.createBoxBody(ObjectManager.getPhysicsWorld(), sprite, bodyType, fixtureDef);
    	} else {
            body = CustomPhysicsFactory.createComplexBody(ObjectManager.getPhysicsWorld(), sprite, bodyType, fixtureDef, VerticesManager.getVertices(region.toString(), poly, sprite));
    	}
    	
        body.setUserData(this);
        
        ObjectManager.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true) {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
                if (shouldDie) { die(); }
                onPerformUpdate();
            }
        });
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
    
    @Override
    public void damageReceived(float damage, Vector2 direction) {
    	if (damage > 0) {
    		livingPoints = livingPoints - damage;
    		damage = damage - livingPoints;
    	}
    	if (livingPoints <= 0) {
    		shouldDie = true;
    	}
    }
    
    @Override
    public void beginContact(Body body) {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    public void endContact(Body body) {
    	// TODO Auto-generated method stub
    
    }
   
    public void onPerformUpdate() {
    	
    }
    
    public abstract void die();
   
}