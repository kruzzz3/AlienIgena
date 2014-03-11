package ch.webk.base.factory;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;

import ch.webk.base.manager.ManagerAnimation;
import ch.webk.base.manager.ManagerObject;
import ch.webk.base.object.IPhysicObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;


public class CustomMethodFactory {

	private static final String TAG = "CustomMethodFactory";
	
	public static void explode(String explosionID, final Sprite parentSprite, final Body body, int frames, float scale, final float damageMultiplikator) {
		AnimatedSprite explosion = ManagerObject.getAnimatedSprite(parentSprite.getX(), parentSprite.getY(), explosionID);
		float size = CustomMathFactory.getSpriteSize(parentSprite) * scale;
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
				ManagerObject.removeFromGameScene(null, arg0);
			}
		};
		ManagerAnimation.startAnimationNormal(explosion, frames, false, animationListener);
		ManagerObject.getGameScene().attachChild(explosion);
		
		final float radiusBox2d = CustomMathFactory.toBox2dValue(size);
		
		ManagerObject.getPhysicsWorld().QueryAABB(new QueryCallback() {
			
			@Override
			public boolean reportFixture(Fixture fixture) {
				float distance = CustomMathFactory.getDistance(body.getWorldCenter(), fixture.getBody().getWorldCenter());
				float impulseX = CustomMathFactory.getDistanceX(body.getWorldCenter(), fixture.getBody().getWorldCenter()) * radiusBox2d / distance;
				float impulseY = CustomMathFactory.getDistanceY(body.getWorldCenter(), fixture.getBody().getWorldCenter()) * radiusBox2d / distance;
				fixture.getBody().applyForce(new Vector2(impulseX, impulseY), fixture.getBody().getWorldCenter());
				try {
		        	((IPhysicObject) fixture.getBody().getUserData()).damageReceived((radiusBox2d * damageMultiplikator) / distance, new Vector2(impulseX, impulseY));
		        } catch(ClassCastException e) { }
				try {
					((IPhysicObject) fixture.getBody().getUserData()).damageReceived((radiusBox2d * damageMultiplikator) / distance, new Vector2(impulseX, impulseY));
				} catch(ClassCastException e) { }
				return true;
			}
		}, body.getPosition().x - radiusBox2d, body.getPosition().y - radiusBox2d, body.getPosition().x + radiusBox2d, body.getPosition().y + radiusBox2d);
	}
	
}
