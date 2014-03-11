package ch.webk.base.manager;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import ch.webk.base.manager.system.ManagerResources;
import ch.webk.scene.GameScene;

import com.badlogic.gdx.physics.box2d.Body;

public class ManagerObject {

	//private static final String TAG = "ObjectManager";
	
	private static PhysicsWorld physicsWorld;
	public static PhysicsWorld getPhysicsWorld() {
		return physicsWorld;
	}
	public static void setPhysicsWorld(PhysicsWorld physicsWorld) {
		ManagerObject.physicsWorld = physicsWorld;
	}
	
	private static Engine engine;
	public static Engine getEngine() {
		return engine;
	}
	public static void setEngine(Engine engine) {
		ManagerObject.engine = engine;
	}

	private static ZoomCamera camera;
	public static ZoomCamera getCamera() {
		return camera;
	}
	public static void setCamera(ZoomCamera camera) {
		ManagerObject.camera = camera;
	}
	
	private static GameScene gameScene;
	public static void setGameScene(GameScene scene) {
		gameScene = scene;
	}
	public static GameScene getGameScene() {
		return gameScene;
	}

	
	
	
	/*
	 * 
	 */
	
	
	public static AnimatedSprite getAnimatedSprite(float x, float y, String id) {
    	final AnimatedSprite animatedSprite = new AnimatedSprite(x, y, ManagerResources.getInstance().animatedObjects.get(id), ManagerResources.getInstance().getVertexBufferObjectManager());
		return animatedSprite;
    }

	
	public static void removeFromGameScene(final Body body, final ArrayList<Sprite> spriteArray) {
		final ArrayList<PhysicsConnector> physicsConnectorArray = new ArrayList<PhysicsConnector>();
		for(Sprite sprite : spriteArray) {
			physicsConnectorArray.add(ManagerObject.getPhysicsWorld().getPhysicsConnectorManager().findPhysicsConnectorByShape(sprite));
		}
		ManagerObject.getEngine().runOnUpdateThread(new Runnable() {
		    @Override
		    public void run() {
		    	for(PhysicsConnector physicsConnector : physicsConnectorArray) {
		    		if (physicsConnector != null) {
		    			ManagerObject.getPhysicsWorld().unregisterPhysicsConnector(physicsConnector);
			        }
				}
		    	if (body != null) {
		    		body.setActive(false);
		    		ManagerObject.getPhysicsWorld().destroyBody(body);
		    	}
		        for(Sprite sprite : spriteArray) {
		        	getGameScene().detachChild(sprite);
				}
		    }
		});
	}
	
	public static void removeFromGameScene(final Body body, final Sprite sprite) {
		final PhysicsConnector physicsConnector = ManagerObject.getPhysicsWorld().getPhysicsConnectorManager().findPhysicsConnectorByShape(sprite);
		ManagerObject.getEngine().runOnUpdateThread(new Runnable() {
		    @Override
		    public void run() {
	    		if (physicsConnector != null) {
	    			ManagerObject.getPhysicsWorld().unregisterPhysicsConnector(physicsConnector);
		        }
		    	if (body != null) {
		    		body.setActive(false);
		    		ManagerObject.getPhysicsWorld().destroyBody(body);
		    	}
		        if (sprite != null) {
		        	getGameScene().detachChild(sprite);
				}
		    }
		});
	}
	
	
	
}
