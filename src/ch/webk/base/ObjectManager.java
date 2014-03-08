package ch.webk.base;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.scene.BaseScene;
import ch.webk.scene.GameScene;

public class ObjectManager {

	//private static final String TAG = "ObjectManager";
	
	private static PhysicsWorld physicsWorld;
	public static PhysicsWorld getPhysicsWorld() {
		return physicsWorld;
	}
	public static void setPhysicsWorld(PhysicsWorld physicsWorld) {
		ObjectManager.physicsWorld = physicsWorld;
	}
	
	private static Engine engine;
	public static Engine getEngine() {
		return engine;
	}
	public static void setEngine(Engine engine) {
		ObjectManager.engine = engine;
	}

	private static ZoomCamera camera;
	public static ZoomCamera getCamera() {
		return camera;
	}
	public static void setCamera(ZoomCamera camera) {
		ObjectManager.camera = camera;
	}
	
	private static GameScene gameScene;
	public static void setGameScene(GameScene scene) {
		gameScene = scene;
	}
	public static GameScene getGameScene() {
		return gameScene;
	}
	
	public static void setScene(BaseScene scene) {
		engine.setScene(scene);
	}
	
	public static BaseScene getScene() {
		return (BaseScene) engine.getScene();
	}
	
	
	
	
	
	/*
	 * 
	 */
	
	public static AnimatedSprite getAnimatedSprite(float x, float y, String id) {
    	final AnimatedSprite animatedSprite = new AnimatedSprite(x, y, ResourcesManager.getInstance().animatedObjects.get(id), ResourcesManager.getInstance().getVertexBufferObjectManager());
		return animatedSprite;
    }
	
	public static void removeArray(final Body body, final ArrayList<Sprite> spriteArray) {
		final ArrayList<PhysicsConnector> physicsConnectorArray = new ArrayList<PhysicsConnector>();
		for(Sprite sprite : spriteArray)
		{
			physicsConnectorArray.add(ObjectManager.getPhysicsWorld().getPhysicsConnectorManager().findPhysicsConnectorByShape(sprite));
		}
		ObjectManager.getEngine().runOnUpdateThread(new Runnable() 
		{
		    @Override
		    public void run() 
		    {
		    	for(PhysicsConnector physicsConnector : physicsConnectorArray)
				{
		    		if (physicsConnector != null)
			        {
		    			ObjectManager.getPhysicsWorld().unregisterPhysicsConnector(physicsConnector);
			        }
				}
		    	if (body != null)
		    	{
		    		body.setActive(false);
		    		ObjectManager.getPhysicsWorld().destroyBody(body);
		    	}
		        for(Sprite sprite : spriteArray)
				{
		        	getGameScene().detachChild(sprite);
				}
		    }
		});
	}
	
	public static void removeOne(final Body body, final Sprite sprite) {
		final PhysicsConnector physicsConnector = ObjectManager.getPhysicsWorld().getPhysicsConnectorManager().findPhysicsConnectorByShape(sprite);
		ObjectManager.getEngine().runOnUpdateThread(new Runnable() 
		{
		    @Override
		    public void run() 
		    {
	    		if (physicsConnector != null)
		        {
	    			ObjectManager.getPhysicsWorld().unregisterPhysicsConnector(physicsConnector);
		        }
		    	if (body != null)
		    	{
		    		body.setActive(false);
		    		ObjectManager.getPhysicsWorld().destroyBody(body);
		    	}
		        if (sprite != null)
				{
		        	getGameScene().detachChild(sprite);
				}
		    }
		});
	}
	
	
	
}
