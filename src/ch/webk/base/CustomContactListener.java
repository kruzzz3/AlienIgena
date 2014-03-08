package ch.webk.base;

import java.util.Random;

import ch.webk.base.object.IPhysicObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CustomContactListener implements ContactListener {

	private static final String TAG = "CustomContactListener";
	
	
	private static float hackX = new Random().nextFloat() / 1000;
	private static float hackY = new Random().nextFloat() / 1000;
	
	@Override
	public void beginContact(Contact contact) {
		try {
        	((IPhysicObject) contact.getFixtureA().getBody().getUserData()).beginContact(contact.getFixtureB().getBody());
        } catch(ClassCastException e) { hackX = new Random().nextFloat() / 1000 * -1; }
		try {
			((IPhysicObject) contact.getFixtureB().getBody().getUserData()).beginContact(contact.getFixtureA().getBody());
		} catch(ClassCastException e) { hackY = new Random().nextFloat() / 1000 * -1; }
		hackX = new Random().nextFloat() / 1000;
		hackY = new Random().nextFloat() / 1000 * -1;
	}

	@Override
	public void endContact(Contact contact) {
		try {
        	((IPhysicObject) contact.getFixtureA().getBody().getUserData()).endContact(contact.getFixtureB().getBody());
        } catch(ClassCastException e) { hackX = new Random().nextFloat() / 1000 * -1; }
		try {
			((IPhysicObject) contact.getFixtureB().getBody().getUserData()).endContact(contact.getFixtureA().getBody());
		} catch(ClassCastException e) { hackY = new Random().nextFloat() / 1000 * -1; }
		hackX = new Random().nextFloat() / 1000 * -1;
		hackY = new Random().nextFloat() / 1000;
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
		float maxContactImpulse = contactImpulse.getNormalImpulses()[0];
		float x = contact.getWorldManifold().getNormal().x;
		float y = contact.getWorldManifold().getNormal().y;
        for(int i = 1; i <contactImpulse.getNormalImpulses().length; i++) {
        	float temp = Math.max(contactImpulse.getNormalImpulses()[i], maxContactImpulse);
        	if (temp < 10E6) {
        		maxContactImpulse = temp;
        	}
        }
        try {
        	if (contact.getFixtureB().getBody().getMass() > 0) {
        		((IPhysicObject) contact.getFixtureA().getBody().getUserData()).damageReceived(20 * maxContactImpulse * contact.getFixtureB().getBody().getMass(), new Vector2(x, y));
        	} else {
        		((IPhysicObject) contact.getFixtureA().getBody().getUserData()).damageReceived(30 * maxContactImpulse, new Vector2(x, y));
        	}
        } catch(ClassCastException e) { hackY = new Random().nextFloat() / 1000 * -1; }
		try {
			if (contact.getFixtureA().getBody().getMass() > 0) {
        		((IPhysicObject) contact.getFixtureB().getBody().getUserData()).damageReceived(20 * maxContactImpulse * contact.getFixtureA().getBody().getMass(), new Vector2(x, y));
        	} else {
        		((IPhysicObject) contact.getFixtureB().getBody().getUserData()).damageReceived(30 * maxContactImpulse, new Vector2(x, y));
        	}
		} catch(ClassCastException e) { hackY = new Random().nextFloat() / 1000 * -1; }
	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
		
	}
	
}
