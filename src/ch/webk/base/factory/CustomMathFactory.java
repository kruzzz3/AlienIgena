package ch.webk.base.factory;

import java.util.Random;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

import ch.webk.base.Logger;

import com.badlogic.gdx.math.Vector2;

public class CustomMathFactory {

	public static float getSpriteSize(Sprite sprite) {
		float w = sprite.getWidth()*sprite.getScaleX();
		float h = sprite.getHeight()*sprite.getScaleY();
		float size = w;
		if (h > w) {
			size = h;
		}
		return size;
	}
	
	public static float getDistance(Vector2 v1, Vector2 v2) {
		float x = v2.x - v1.x;
		float y = v2.y - v1.y;
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public static float getDistanceX(Vector2 v1, Vector2 v2) {
		return v2.x - v1.x;
	}
	
	public static float getDistanceY(Vector2 v1, Vector2 v2) {
		return v2.y - v1.y;
	}
	
	public static Vector2 getScaledVector(Vector2 v, float scaleTo) {
		return v.mul(scaleTo / v.len());
	}
	
	public static Vector2 invertVector(Vector2 v) {
		v.x *= -1;
		v.y *= -1;
		return v;
	}
	
	public static float toAndEngineValue(float value) {
		return value * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	}
	
	public static float toBox2dValue(float value) {
		return value / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	}
	
	public static int getRandInt(int min, int max) {
		Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
}
