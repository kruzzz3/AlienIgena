package ch.webk.base.object;

import java.util.ArrayList;

import org.andengine.entity.sprite.Sprite;

import ch.webk.base.Logger;
import ch.webk.base.manager.ManagerObject;
import ch.webk.base.manager.system.ManagerResources;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class PhysicObjectWithSpriteNormal extends PhysicObjectWithSprite {

	private String id;
	
	public PhysicObjectWithSpriteNormal(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, float sx, float sy) {
		super(x, y, fixtureDef, bodyType, ManagerResources.getInstance().objectsVertex.get(id), ManagerResources.getInstance().staticObjects.get(id), sy, sy);
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void dispose(ArrayList<Sprite> spriteArray) {
		if (spriteArray == null) {
			spriteArray = new ArrayList<Sprite>();
		}
		spriteArray.add(getSprite());
		disposeRest(spriteArray);
	}
    
}