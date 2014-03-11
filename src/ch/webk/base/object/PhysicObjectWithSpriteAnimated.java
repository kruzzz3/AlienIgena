package ch.webk.base.object;

import java.util.ArrayList;

import org.andengine.entity.sprite.Sprite;

import ch.webk.base.Logger;
import ch.webk.base.manager.system.ManagerResources;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class PhysicObjectWithSpriteAnimated  extends PhysicObjectWithSprite {
	
	private String id;
	
	public PhysicObjectWithSpriteAnimated(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, float sx, float sy) {
		super(x, y, fixtureDef, bodyType, ManagerResources.getInstance().objectsVertex.get(id), ManagerResources.getInstance().animatedObjects.get(id), sy, sy);
		Logger.i("PhysicObjectWithSpriteAnimated", "PhysicObjectWithSpriteAnimated");
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
    
	public void dispose(ArrayList<Sprite> spriteArray) {
		if (spriteArray == null) {
			spriteArray = new ArrayList<Sprite>();
		}
		spriteArray.add(getAnimatedSprite());
		disposeRest(spriteArray);
	}
	 
}