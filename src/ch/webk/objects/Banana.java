package ch.webk.objects;

import org.andengine.opengl.texture.region.ITiledTextureRegion;

import ch.webk.base.AnimationManager;
import ch.webk.base.object.PhysicObjectWithSpriteAnimated;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Banana extends PhysicObjectWithSpriteAnimated {

	public Banana(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, ITiledTextureRegion region, float sx, float sy) {
		super(id, x, y, fixtureDef, bodyType, region, sx, sy);
		AnimationManager.startAnimationNormal(getAnimatedSprite(), 100, true, null);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
