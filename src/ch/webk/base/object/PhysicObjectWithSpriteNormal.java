package ch.webk.base.object;

import org.andengine.opengl.texture.region.ITextureRegion;

import ch.webk.base.ResourcesManager;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class PhysicObjectWithSpriteNormal extends PhysicObjectWithSprite {

	private String id;
	
	public PhysicObjectWithSpriteNormal(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, ITextureRegion  region, float sx, float sy) {
		super(x, y, fixtureDef, bodyType, ResourcesManager.getInstance().objectsVertex.get(id), region, sy, sy);
		this.id = id;
	}
    
	public String getId() {
		return id;
	}
    
	 public void onManageUpdate() {
    	// DO NOTHING
    }

}