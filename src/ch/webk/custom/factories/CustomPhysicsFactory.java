package ch.webk.custom.factories;

import org.andengine.entity.shape.IShape;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.util.Constants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class CustomPhysicsFactory {	
	
	public static Body createComplexBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final BodyType pBodyType, final FixtureDef pFixtureDef, Vector2[][] vertices) {
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pIShape.getSceneCenterCoordinates();
		bodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		bodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

		final Body body = pPhysicsWorld.createBody(bodyDef);

		PolygonShape polyShape = new PolygonShape();
		pFixtureDef.shape = polyShape;

		final int length = vertices.length;
		for (int i = 0; i < length; i++) {
			polyShape.set(vertices[i]);
			body.createFixture(pFixtureDef);
		}
		polyShape.dispose();

		return body;
	}
	
	public static Body createCircleBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pIShape.getSceneCenterCoordinates();
		bodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		bodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

		final Body body = pPhysicsWorld.createBody(bodyDef);

		CircleShape circleShape = new CircleShape();
		
		float w = pIShape.getWidth() * pIShape.getScaleX();
		float h = pIShape.getHeight() * pIShape.getScaleY();
		float targetSize = w;
		if (h > w) {
			targetSize = h;
		}
		circleShape.setRadius((targetSize/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		pFixtureDef.shape = circleShape;

		body.createFixture(pFixtureDef);
		circleShape.dispose();

		return body;
	}
	
	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pIShape.getSceneCenterCoordinates();
		bodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		bodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

		final Body body = pPhysicsWorld.createBody(bodyDef);
		
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(pIShape.getWidth(), pIShape.getHeight());
	
		pFixtureDef.shape = boxShape;

		body.createFixture(pFixtureDef);
		boxShape.dispose();

		return body;
	}

}