package ch.webk.base;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.entity.shape.IShape;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

import com.badlogic.gdx.math.Vector2;

public class VerticesManager {

	public static float CENTER = 0.5f;
	
	public static ArrayList<Vector2[][]> vertices = new ArrayList<Vector2[][]>();
	public static HashMap<String, Integer> map = new HashMap<String, Integer>();
	
	public static Vector2[][] getVertices(String type, String json, IShape pIShape) {
		String mapIndex = type + "_" + (pIShape.getWidth() * pIShape.getScaleX()) + "_" + (pIShape.getHeight() * pIShape.getScaleY());
		int index = -1;
		try {
			index = map.get(mapIndex);
		} catch (NullPointerException e) {
			index = -1;
		}
		if (index >= 0) {
			return vertices.get(index);
		} else {
			final int pos = map.size();
			map.put(mapIndex, pos);
			vertices.add(pos, createVertices(json, pIShape));
			return getVertices(type, json, pIShape);
		}
	}
	
	private static Vector2[][] createVertices(String json, IShape pIShape) {
		json = json.replaceAll("\\[\\[", "");
		json = json.replaceAll("\\]\\]", "");
		json = json.replaceAll("...:", "");
        float scaleX = PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT / (pIShape.getWidth() * pIShape.getScaleX());
        float scaleY = PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT / (pIShape.getHeight() * pIShape.getScaleY());  
		String[] vector = json.split("\\],\\[");
		Vector2[][] vertices = new Vector2[vector.length][];
		int i = 0;
		for (String v : vector) {
			vertices[i] = getVector2(v, scaleX, scaleY);
			i++;
		}
		return vertices;
	}
	
	private static Vector2[] getVector2(String input, float scaleX, float scaleY) {
		String[] v_strarray = input.split("\\},\\{");
		Vector2[] v = new Vector2[v_strarray.length];
		int i = 0;
		for (String v_str : v_strarray) {
			v_str = v_str.replaceAll("\\{", "");
			v_str = v_str.replaceAll("\\}", "");
			String[] xy = v_str.split(",");
		    v[i] = new Vector2((Float.parseFloat(xy[0]) - CENTER) / scaleX, (Float.parseFloat(xy[1]) - CENTER) / scaleY);
			i++;
		}
		return v;
	}

}