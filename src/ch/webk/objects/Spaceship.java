package ch.webk.objects;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import ch.webk.base.factory.CustomJointFactory;
import ch.webk.base.factory.CustomMathFactory;
import ch.webk.base.manager.ManagerObject;
import ch.webk.base.object.PhysicObjectWithSpriteNormal;
import ch.webk.base.object.manipulate.PhysicObjectMove;
import ch.webk.objects.parts.IShieldListener;
import ch.webk.objects.parts.Shield;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Spaceship extends PhysicObjectWithSpriteNormal{

	private static final String TAG = "Spaceship";
	
	private PhysicObjectMove physicObjectMove;
		
	private Shield shield;
	private boolean hasShield = true;
	
	public Spaceship(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, float sx, float sy) {
		super(id, x, y, fixtureDef, bodyType, sx, sy);
		ManagerObject.getCamera().setChaseEntity(getSprite());
		initLivingPoints(100);
		createShield();
		
		physicObjectMove = new PhysicObjectMove(getBody(), 0.1f, 0.1f, 20);
		physicObjectMove.setTarget(new Vector2(150, 150));
	}
	
	private void createShield() {
		float scale = (CustomMathFactory.getSpriteSize(getSprite()) / 200) * 1.8f;
		shield = new Shield("shield_001", getSprite().getX(), getSprite().getY(), PhysicsFactory.createFixtureDef(0, 0, 0), BodyType.DynamicBody, scale, scale, new IShieldListener() {
				
			@Override
			public void onShieldIsEmpty() {
				hasShield = false;
			}
			
			@Override
			public void onShieldHasCapacity() {
				hasShield = true;
			}

			@Override
			public void onShieldPointsCreated(AnimatedSprite shieldPoints) {
				ManagerObject.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(shieldPoints, getBody(), true, false));
				
			}
		});
		shield.initLivingPoints(100);
		CustomJointFactory.createRevJointLimitless(getBody(), shield.getBody(), 0, 0, false);
	}
	
	@Override
	public void damageReceived(float damage, Vector2 direction) {
		if (!hasShield) {
			super.damageReceived(damage, direction);
		}
	}
	
	@Override
	public void onPerformUpdate() {
		super.onPerformUpdate();
		physicObjectMove.update();
	}

	@Override
	public void die() { }

}
