package ch.webk.objects;

import ch.webk.base.object.PhysicObjectWithSpriteNormal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Planet extends PhysicObjectWithSpriteNormal {
	
	public Planet(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, float sx, float sy) {
		super(id, x, y, fixtureDef, bodyType, sx, sy);
	}

	@Override
	public void die() { }
	
	@Override
	public void damageReceived(float damage, Vector2 direction) { }

}
