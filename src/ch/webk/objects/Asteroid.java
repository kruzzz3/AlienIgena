package ch.webk.objects;

import java.util.Random;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import ch.webk.base.Logger;
import ch.webk.base.ObjectManager;
import ch.webk.base.object.PhysicObjectWithSpriteNormal;
import ch.webk.custom.factories.CustomMethodFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Asteroid extends PhysicObjectWithSpriteNormal {

	private static final String TAG = "Asteroid";
	
	private int counter = 0;
	private int ex = randInt(10,50);
	
	public Asteroid(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, ITextureRegion region, float sx, float sy) {
		super(id, x, y, fixtureDef, bodyType, region, sx, sy);
		setLivingPoints(100);
	}
	
	@Override
	public void onPerformUpdate() {
		super.onPerformUpdate();
		if (counter == ex) {
			if (getId().equals("asteroid_002")) {
				die();
			}
		}
		counter++;
	}
	
	 private int randInt(int min, int max) {

	        // Usually this can be a field rather than a method variable
	        Random rand = new Random();

	        // nextInt is normally exclusive of the top value,
	        // so add 1 to make it inclusive
	        int randomNum = rand.nextInt((max - min) + 1) + min;

	        return randomNum;
	    }

	@Override
	public void die() {
		CustomMethodFactory.explode("explosion_002", getSprite(), getBody(), 60, 5);
	}

}
