package ch.webk.base;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import ch.webkch.spacefighter.R;

public class ObjectDefinitions{
	
	private static final String TAG = "ObjectsLoader";
	
	public static ArrayList<InputStream> objectDefinitions;
	public static int counter;
	
	public static void init(Context context) {
		Logger.i(TAG,"init");
		objectDefinitions = new ArrayList<InputStream>();
		
		// Backgrounds
		addToList(context.getResources().openRawResource(R.raw.background_001));
		// System
		addToList(context.getResources().openRawResource(R.raw.wall_001));
		// Asteroids
		addToList(context.getResources().openRawResource(R.raw.asteroid_001));
		addToList(context.getResources().openRawResource(R.raw.asteroid_002));
		addToList(context.getResources().openRawResource(R.raw.asteroid_003));
		// Planets
		addToList(context.getResources().openRawResource(R.raw.planet_001));
		addToList(context.getResources().openRawResource(R.raw.planet_002));
		addToList(context.getResources().openRawResource(R.raw.planet_003));
		addToList(context.getResources().openRawResource(R.raw.planet_004));
		// Spaceship
		addToList(context.getResources().openRawResource(R.raw.spaceship_001));
		addToList(context.getResources().openRawResource(R.raw.spaceship_002));
		// Sun
		addToList(context.getResources().openRawResource(R.raw.sun_001));
		// Explosion
		addToList(context.getResources().openRawResource(R.raw.explosion_001));
		addToList(context.getResources().openRawResource(R.raw.explosion_002));
		// Banana
		addToList(context.getResources().openRawResource(R.raw.banana_001));
	}
	
	private static void addToList(InputStream newItem) {
		objectDefinitions.add(newItem);
		counter++;
	}

}