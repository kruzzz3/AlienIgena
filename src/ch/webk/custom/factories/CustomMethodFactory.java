package ch.webk.custom.factories;
import java.util.Random;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

import ch.webk.base.AnimationManager;
import ch.webk.base.Logger;
import ch.webk.base.ObjectManager;


public class CustomMethodFactory {

	private static final String TAG = "CustomMethodFactory";
	
	
	public static void explode(String explosionID, final Sprite parentSprite, final Body body, int frames, float force) {
		AnimatedSprite explosion = ObjectManager.getAnimatedSprite(parentSprite.getX(), parentSprite.getY(), explosionID);
		float size = size(parentSprite) * force;
		explosion.setWidth(size);
		explosion.setHeight(size); 
		IAnimationListener animationListener = new IAnimationListener() {	
			@Override
			public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
			}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
			}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite arg0) {
				ObjectManager.removeOne(null, arg0);
			}
		};
		AnimationManager.startAnimationNormal(explosion, frames, false, animationListener);
		ObjectManager.getGameScene().attachChild(explosion);
		
		final float radiusBox2d = size / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		Logger.i(TAG, "radiusBox2d = "+radiusBox2d);
		final float impulse = 0.1f;
		
		ObjectManager.getPhysicsWorld().QueryAABB(new QueryCallback() {
			
			@Override
			public boolean reportFixture(Fixture fixture) {
				Logger.i(TAG, "reportFixture");
	                  
				float distance = distance(body.getWorldCenter(), fixture.getBody().getWorldCenter());
				float impulseX = distanceX(body.getWorldCenter(), fixture.getBody().getWorldCenter()) * impulse / distance;
				float impulseY = distanceY(body.getWorldCenter(), fixture.getBody().getWorldCenter()) * impulse / distance;
				fixture.getBody().applyLinearImpulse(new Vector2(impulseX, impulseY), fixture.getBody().getWorldCenter());
				return true;
			}
		}, body.getPosition().x - radiusBox2d, body.getPosition().y - radiusBox2d, body.getPosition().x + radiusBox2d, body.getPosition().y + radiusBox2d);
		ObjectManager.removeOne(body, parentSprite);
	}

	private static float size(Sprite sprite) {
		
		float w = sprite.getWidth()*sprite.getScaleX();
		float h = sprite.getHeight()*sprite.getScaleY();
		float size = w;
		if (h > w) {
			size = h;
		}
		return size;
	}
	
	private static float distance(Vector2 v1, Vector2 v2) {
		float x = v2.x -v1.x;
		float y = v2.y -v1.y;
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	private static float distanceX(Vector2 v1, Vector2 v2) {
		return v2.x -v1.x;
	}
	
	private static float distanceY(Vector2 v1, Vector2 v2) {
		return v2.y -v1.y;
	}
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
}
