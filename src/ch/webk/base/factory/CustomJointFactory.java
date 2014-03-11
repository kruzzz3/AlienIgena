package ch.webk.base.factory;

import ch.webk.base.manager.ManagerObject;
import ch.webk.base.manager.system.ManagerResources;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class CustomJointFactory {

	public static DistanceJointDef createDisJoint(String uId1, String uId2, float dampingRatio, float frequencyHz, float length) {
    	DistanceJointDef disJointDef = new DistanceJointDef();
    	Body b1 = ManagerResources.getInstance().objectBody.get(uId1);
    	Body b2 = ManagerResources.getInstance().objectBody.get(uId2);
    	disJointDef.initialize(b1, b2, b1.getWorldCenter(), b2.getWorldCenter());
    	disJointDef.dampingRatio = dampingRatio;
    	disJointDef.frequencyHz = frequencyHz;
    	disJointDef.collideConnected = false;
    	disJointDef.length = length;
    	ManagerObject.getPhysicsWorld().createJoint(disJointDef);
    	return disJointDef;
	}
	
	public static DistanceJointDef createDisJoint(Body b1, Body b2, float dampingRatio, float frequencyHz, float length) {
    	DistanceJointDef disJointDef = new DistanceJointDef();
    	disJointDef.initialize(b1, b2, b1.getWorldCenter(), b2.getWorldCenter());
    	disJointDef.dampingRatio = dampingRatio;
    	disJointDef.frequencyHz = frequencyHz;
    	disJointDef.collideConnected = false;
    	disJointDef.length = length;
    	ManagerObject.getPhysicsWorld().createJoint(disJointDef);
    	return disJointDef;
	}
	
	public static RevoluteJointDef createRevJointLimitless(String uId1, String uId2, float maxMotorTorquem, float maxMotorSpeed, boolean enableMotor) {
		RevoluteJointDef revJointDef = new RevoluteJointDef();
    	Body b1 = ManagerResources.getInstance().objectBody.get(uId1);
    	Body b2 = ManagerResources.getInstance().objectBody.get(uId2);
    	revJointDef.initialize(b1, b2, b1.getWorldCenter());
    	revJointDef.maxMotorTorque = maxMotorTorquem; 
    	revJointDef.motorSpeed = maxMotorSpeed;
    	revJointDef.enableMotor = enableMotor; 
    	revJointDef.enableLimit = false;
    	ManagerObject.getPhysicsWorld().createJoint(revJointDef);
    	return revJointDef;
	}
	
	public static RevoluteJointDef createRevJointLimitless(Body b1, Body b2, float maxMotorTorquem, float maxMotorSpeed, boolean enableMotor) {
		RevoluteJointDef revJointDef = new RevoluteJointDef();
    	revJointDef.initialize(b1, b2, b1.getWorldCenter());
    	revJointDef.maxMotorTorque = maxMotorTorquem; 
    	revJointDef.motorSpeed = maxMotorSpeed;
    	revJointDef.enableMotor = enableMotor; 
    	revJointDef.enableLimit = false;
    	ManagerObject.getPhysicsWorld().createJoint(revJointDef);
    	return revJointDef;
	}
}
