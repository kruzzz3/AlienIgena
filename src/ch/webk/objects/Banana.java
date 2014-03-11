package ch.webk.objects;

import ch.webk.base.Logger;
import ch.webk.base.manager.ManagerAnimation;
import ch.webk.base.object.PhysicObjectWithSpriteAnimated;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Banana extends PhysicObjectWithSpriteAnimated {

	public Banana(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, float sx, float sy) {
		super(id, x, y, fixtureDef, bodyType, sx, sy);
		Logger.i("Banana", "INIT1");
		ManagerAnimation.startAnimationNormal(getAnimatedSprite(), 100, true, null);
		Logger.i("Banana", "INIT2");
	}

	@Override
	public void die() { }
	
	@Override
	public void damageReceived(float damage, Vector2 direction) { }

}
