package ch.webk.objects;

import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.texture.region.ITextureRegion;

import ch.webk.base.ObjectManager;
import ch.webk.base.object.PhysicObjectWithSpriteNormal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Spaceship extends PhysicObjectWithSpriteNormal{

	private static final String TAG = "Spaceship";
	
	private Vector2 destinationPoint;
	private float maxAcceleration = 0.01f;
	private float maxBreak = 0.05f;
	private float distanceToStartBreak = 0;
	private float currentSpeed = 0f;
	private float maxSpeed = 15f;
	
	public Spaceship(String id, float x, float y, FixtureDef fixtureDef, BodyType bodyType, ITextureRegion region, float sx, float sy) {
		super(id, x, y, fixtureDef, bodyType, region, sx, sy);
		ObjectManager.getCamera().setChaseEntity(getSprite());
		getBody().applyForce(new Vector2(10, 10), getBody().getWorldCenter());
		distanceToStartBreak = ( (maxSpeed / maxBreak) ) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	}
	
	@Override
	public void onManageUpdate() {
		super.onManageUpdate();
		destinationPoint = ObjectManager.getGameScene().clickPoint;
		moveToPoint();
		
	}
	
	private void moveToPoint() {
		float x = destinationPoint.x - getBody().getPosition().x;
		float y = destinationPoint.y - getBody().getPosition().y;
		move(x, y);
	}
	
	private void move(float x, float y) {
		float distance = distance(getBody().getPosition().x, getBody().getPosition().y, destinationPoint.x, destinationPoint.y);
		if (distance > distanceToStartBreak) {
			currentSpeed += maxAcceleration;
			doit(x,y);
		} else if (distance > PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT) {
			currentSpeed -= maxBreak;
			doit(x,y);
		} else {
			getBody().setLinearVelocity(0, 0);
		}
	}
	
	private void doit(float x, float y) {
		Body body = getBody();
		float c = (float) Math.sqrt(x*x + y*y);
		
		if (currentSpeed >= maxSpeed) {currentSpeed = maxSpeed;}
		float scale = currentSpeed / c;
		float angle = 0;
		if (y < 0)
		{
			angle = (float) (Math.acos(-x/c) + Math.PI/2);
		}
		else
		{
			angle = (float) (Math.acos(x/c) - Math.PI/2);
		}
		body.setTransform(getBody().getPosition(), angle);
		body.setLinearVelocity(x * scale, y * scale);
		body.setAngularVelocity(0);
	}
	
	private float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
