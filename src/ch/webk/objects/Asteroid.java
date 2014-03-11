package ch.webk.objects;

import ch.webk.base.factory.CustomMathFactory;
import ch.webk.base.factory.CustomMethodFactory;
import ch.webk.base.object.PhysicObjectWithSpriteNormal;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Asteroid extends PhysicObjectWithSpriteNormal {

	private static final String TAG = "Asteroid";
	
	public Asteroid(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, float sx, float sy) {
		super(id, x, y, fixtureDef, bodyType, sx, sy);
		initLivingPoints(CustomMathFactory.getSpriteSize(getSprite())/10);
	}
	
	@Override
	public void onPerformUpdate() {
		super.onPerformUpdate();
	}

	@Override
	public void die() {
		CustomMethodFactory.explode("explosion_002", getSprite(), getBody(), 60, 5, 1);
		dispose(null);
	}

}
