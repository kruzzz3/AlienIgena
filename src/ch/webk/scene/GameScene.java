package ch.webk.scene;

import java.util.Random;

import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;

import ch.webk.base.CustomContactListener;
import ch.webk.base.LevelLoader;
import ch.webk.base.Logger;
import ch.webk.base.ObjectManager;
import ch.webk.base.ResourcesManager;
import ch.webk.base.object.PhysicObjectWithSprite;
import ch.webk.custom.factories.CustomMethodFactory;
import ch.webk.objects.Asteroid;
import ch.webk.scene.SceneManager.SceneType;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GameScene extends BaseScene {	
	
	private static final String TAG = "GameScene";
	
	@Override
	public void createScene() {
		Logger.i(TAG,"createScene");
		ObjectManager.setGameScene(this);
	    createPhysics();
	    LevelLoader.loadLevel(1);
	}

	@Override
	public void onBackKeyPressed() {
		Logger.i(TAG,"onBackKeyPressed");
	    SceneManager.getInstance().loadMenuScene();
	}

    @Override
    public SceneType getSceneType() {
    	Logger.i(TAG,"getSceneType");
        return SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene() {
    	Logger.i(TAG,"disposeScene");  	
    	super.resetCamera();
    }
    
    private void createPhysics() {
    	Logger.i(TAG,"createPhysics");
    	ObjectManager.setPhysicsWorld(new FixedStepPhysicsWorld(40, new Vector2(0,0), false)); 
    	ObjectManager.getPhysicsWorld().setContactListener(new CustomContactListener());
        registerUpdateHandler(ObjectManager.getPhysicsWorld());
    }
    
    
    
    public Vector2 clickPoint = new Vector2();
    
    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
    	Logger.i(TAG,"onSceneTouchEvent | pSceneTouchEvent="+pSceneTouchEvent);
    	if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
    	{
    		
    		clickPoint.set(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
    		
    		float scale = 3 * new Random().nextFloat();
    		PhysicObjectWithSprite levelObject = new Asteroid("asteroid_003", pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), PhysicsFactory.createFixtureDef(0.8f, 0, 0), BodyType.DynamicBody, ResourcesManager.getInstance().staticObjects.get("asteroid_003"), scale, scale);
    		levelObject.getBody().applyForce(new Vector2(-300*scale,0), levelObject.getBody().getWorldCenter());
    		attachChild(levelObject.getSprite());
    	}
    	return super.onSceneTouchEvent(pSceneTouchEvent);
    }
    
	@Override
	public void onMenuKeyPressed() {
		Logger.i(TAG,"onMenuKeyPressed");
	}
	
	@Override
	public void onVolUpKeyPressed() {
		Logger.i(TAG,"onVolUpKeyPressed");
		ObjectManager.getCamera().setZoomFactor(ObjectManager.getCamera().getZoomFactor()+0.005f);
	}

	@Override
	public void onVolDownKeyPressed() {
		Logger.i(TAG,"onVolDownKeyPressed");
		if (ObjectManager.getCamera().getZoomFactor() > 0.005f)
		{	
			ObjectManager.getCamera().setZoomFactor(ObjectManager.getCamera().getZoomFactor()-0.005f);
		}
	}
	
	@Override
	public void resetCamera() {
		// DO NOT CALL SUPER
	}
    
}
