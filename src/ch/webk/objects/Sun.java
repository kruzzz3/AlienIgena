package ch.webk.objects;

import org.andengine.opengl.texture.region.ITextureRegion;

import ch.webk.base.object.PhysicObjectWithSpriteNormal;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Sun extends PhysicObjectWithSpriteNormal{

	public Sun(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, ITextureRegion region, float sx, float sy) {
		super(id, x, y, fixtureDef, bodyType, region, sx, sy);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
