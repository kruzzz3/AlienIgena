package ch.webk.base;

import ch.webk.base.object.IPhysicObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CustomContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		try {
        	((IPhysicObject) contact.getFixtureA().getBody().getUserData()).beginContact(contact.getFixtureB().getBody());
        } catch(ClassCastException e) { }
		try {
			((IPhysicObject) contact.getFixtureB().getBody().getUserData()).beginContact(contact.getFixtureA().getBody());
		} catch(ClassCastException e) { }
	}

	@Override
	public void endContact(Contact contact) {
		try {
        	((IPhysicObject) contact.getFixtureA().getBody().getUserData()).endContact(contact.getFixtureB().getBody());
        } catch(ClassCastException e) { }
		try {
			((IPhysicObject) contact.getFixtureB().getBody().getUserData()).endContact(contact.getFixtureA().getBody());
		} catch(ClassCastException e) { }
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
        	((IPhysicObject) contact.getFixtureA().getBody().getUserData()).damageReceived(maxContactImpulse, new Vector2(x, y));
        } catch(ClassCastException e) { }
		try {
			((IPhysicObject) contact.getFixtureB().getBody().getUserData()).damageReceived(maxContactImpulse, new Vector2(x, y));
		} catch(ClassCastException e) { }
	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
	}
	
}
