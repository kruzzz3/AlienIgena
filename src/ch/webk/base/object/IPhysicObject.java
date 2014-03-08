package ch.webk.base.object;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface IPhysicObject {


	
	public abstract void damageReceived(float damage, Vector2 direction);
	public abstract void beginContact(Body body);
	public abstract void endContact(Body body);
	
	public abstract void die();
}
