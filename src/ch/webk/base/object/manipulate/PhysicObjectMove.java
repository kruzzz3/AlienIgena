package ch.webk.base.object.manipulate;

import ch.webk.base.Logger;
import ch.webk.base.factory.CustomMathFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicObjectMove {

	private Body body;
	
	private Vector2 target;
	
	private float maxAccelerationVelocity;
	private float maxAccelerationAngle;
	private float curSpeed = 0;
	private float maxSpeed;
	private float curBreak = 0;
	private float direction = 0;
	
	private int counter = 0;
	
	public PhysicObjectMove(Body body, float maxAccelerationVelocity, float maxAccelerationAngle, float maxSpeed) {
		this.body = body;
		this.maxAccelerationVelocity = CustomMathFactory.toBox2dValue(maxAccelerationVelocity);
		this.maxAccelerationAngle = CustomMathFactory.toBox2dValue(maxAccelerationAngle);
		this.maxSpeed = CustomMathFactory.toBox2dValue(maxSpeed);
		this.curBreak = -maxSpeed;
	}
	
	
	
	public void update() {
		if (target != null) {
			//move();
		}
		if (counter == 500) {
			body.applyLinearImpulse(CustomMathFactory.invertVector(CustomMathFactory.getScaledVector(body.getWorldCenter().sub(target), maxAccelerationVelocity)), body.getWorldCenter());
			Logger.i("MOVE", "vel="+body.getLinearVelocity());
		}
		counter++;
	}
	
	private void move() {
		/*
		if(shouldBreak()) {
			
			body.applyLinearImpulse(-body.getLinearVelocity().x, -body.getLinearVelocity().y, body.getWorldCenter().x, body.getWorldCenter().x);
		}
		*/
	}
	
	private boolean shouldBreak() {
		curSpeed = CustomMathFactory.getDistance(body.getLinearVelocity(), new Vector2(0, 0));
		Logger.i("MOVE", "curSpeed="+curSpeed);
		float dis = CustomMathFactory.getDistance(body.getWorldCenter(), target);
		if (dis > curSpeed) { return false; }
		else{ return true; }
	}
	/*
	private void updateSpeed() {
		float remainDis = CustomMathFactory.getDistance(body.getWorldCenter(), target);
		float neededDisToBreak = 0;
		
		//if (direction >= 0) { neededDisToBreak = getNeededDistanceToBreak(curSpeed); }
		//else { neededDisToBreak = getNeededDistanceToBreak(-curBreak); }
		//neededDisToBreak = getNeededDistanceToBreak(); 
		
		Logger.i("MOVE", "cS="+CustomMathFactory.getDistance(body.getLinearVelocity(), new Vector2(0, 0))+", mV="+maxAccelerationVelocity+", db="+neededDisToBreak+", rd="+remainDis);
		if (remainDis > neededDisToBreak) {
			accelerating();
		} else {
			breaking();
		}
	}
	
	private float getDistanceToBreak() {
		float distanceToBreak = 0;
		float cur = CustomMathFactory.getDistance(body.getLinearVelocity(), new Vector2(0, 0));
		if (cur != 0) {
			float interval = cur / maxAccelerationVelocity;
			distanceToBreak = cur + ((maxAccelerationVelocity / 2) * interval);
		} 
		return distanceToBreak;
	}
	
	
	*/
	
	private void accelerating() {
		curBreak = 0;
		direction = 1;
		curSpeed += maxAccelerationVelocity;
		if (curSpeed > maxSpeed) {
			curSpeed = maxSpeed;
		}
	}
	
	private void breaking() {
		curSpeed = 0;
		direction = -1;
		curBreak -= maxAccelerationVelocity;
		if (curBreak < -maxSpeed) {
			curBreak = -maxSpeed;
		}
	}
	

	public void setTarget(Vector2 target) {
		float x = CustomMathFactory.toBox2dValue(target.x);
		float y = CustomMathFactory.toBox2dValue(target.y); 
		this.target = new Vector2(x, y);
		Vector2 vB = body.getWorldCenter();
		Logger.i("MOVE", "vB | vX="+vB.x+", vY="+vB.y);
		Vector2 vT = this.target;
		Logger.i("MOVE", "vT | vX="+vT.x+", vY="+vT.y);
		Vector2 v = CustomMathFactory.getScaledVector(vT.sub(vB), maxAccelerationVelocity);
		Logger.i("MOVE", "v  | vX="+v.x+", vY="+v.y);
		//body.applyLinearImpulse(v.x, v.y, vB.x, vB.y);
		Logger.i("MOVE", "vel="+body.getLinearVelocity());
		body.applyLinearImpulse(v, vB);
		Logger.i("MOVE", "vel="+body.getLinearVelocity());
		
	}
	
	
	/*
	
	
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
	
	*/
}
