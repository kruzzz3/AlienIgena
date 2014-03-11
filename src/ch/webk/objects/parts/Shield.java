package ch.webk.objects.parts;

import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;

import ch.webk.base.Logger;
import ch.webk.base.manager.ManagerObject;
import ch.webk.base.object.PhysicObjectWithSpriteNormal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Shield extends PhysicObjectWithSpriteNormal {

	private IShieldListener listener;
	private Timer timer = new Timer();
	private boolean isRefreshing = false;
	private boolean shouldActivate = false;
	
	public Shield(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, float sx, float sy, IShieldListener listener) {
		super(id, x, y, fixtureDef, bodyType, sx, sy);
		this.listener = listener;
		getBody().setFixedRotation(true);
		listener.onShieldPointsCreated(getAnimatedLivingPoints());
	}

	@Override
	protected void createLivingPoints() {
		setAnimatedLivingPoints(ManagerObject.getAnimatedSprite(getSprite().getX(), getSprite().getY(), "shieldpoints"));
		Logger.i("SHIELD", "createLivingPoints | 1");
		getAnimatedLivingPoints().setOffsetCenterY(3.5f);
		Logger.i("SHIELD", "createLivingPoints | 2");
		getAnimatedLivingPoints().setScale(0.8f);
		Logger.i("SHIELD", "createLivingPoints | 3");
		ManagerObject.getGameScene().attachChild(getAnimatedLivingPoints());
		Logger.i("SHIELD", "createLivingPoints | 4");
		
		Logger.i("SHIELD", "createLivingPoints | 5");
	}
	
	@Override
	public void die() {
		deactivate();
	}
	
	public void deactivate() {
		for(Fixture fd : getBody().getFixtureList()) {
			fd.setSensor(true);
		}
		getSprite().setVisible(false);
		listener.onShieldIsEmpty();
	}
	
	public void activate() {
		for(Fixture fd : getBody().getFixtureList()) {
			fd.setSensor(false);
		}
		getSprite().setVisible(true);
		listener.onShieldHasCapacity();
	}
	
	@Override
	public void onPerformUpdate() {
		super.onPerformUpdate();
		if (shouldActivate) { shouldActivate = false; activate(); }
	}
	
	private void refreshing() {
		if (!isRefreshing) {
			isRefreshing = true;
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					fill();
				}
			}, 3000);	
		}
	}
	
	private void fill() {
		setLivingPoints(getLivingPoints()+10);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (getLivingPoints() >= getMaxLivingPoints()) {
					setLivingPoints(getMaxLivingPoints());
					shouldActivate = true;
				}
				int currentTile = 10-Math.round(getLivingPoints() / getMaxLivingPoints() * 10);
				getAnimatedLivingPoints().setCurrentTileIndex(currentTile);
				if (getLivingPoints() < getMaxLivingPoints()) {
					fill();
				}
			}
		}, 500);
	}

	@Override
	public void beginContact(Body body) {
		super.beginContact(body);
	}
	
	@Override
	public void damageReceived(float damage, Vector2 direction) {
		super.damageReceived(damage, direction);
		if (damage > 0) {
			int currentTile = 10-Math.round(getLivingPoints() / getMaxLivingPoints() * 10);
			getAnimatedLivingPoints().setCurrentTileIndex(currentTile);
			timer.cancel();
			timer.purge();
			timer = new Timer();
			isRefreshing = false;
			refreshing();
		}
	}
	
	@Override
	public void endContact(Body body) {
		super.endContact(body);
	}
}
