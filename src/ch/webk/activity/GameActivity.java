package ch.webk.activity;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.controller.MultiTouchController;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;
import ch.webk.base.Logger;
import ch.webk.base.manager.ManagerObject;
import ch.webk.base.manager.system.ManagerResources;
import ch.webk.base.object.PhysicObjectWithSpriteDefinition;
import ch.webk.base.object.PhysicObjectWithSpriteLoader;
import ch.webk.scene.SceneManager;

public class GameActivity extends BaseGameActivity {
	
	private static final String TAG = "GameActivity";
    
    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions) {
    	Logger.i(TAG,"onCreateEngine");
    	Engine e = new LimitedFPSEngine(pEngineOptions, 40);
        e.setTouchController(new MultiTouchController());
        ManagerObject.setEngine(e);
        return e;
    }

    public EngineOptions onCreateEngineOptions() {
    	Logger.i(TAG,"onCreateEngineOptions");
    	ManagerObject.setCamera(new ZoomCamera(0, 0, 800, 480));
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 480), ManagerObject.getCamera());
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
    	Logger.i(TAG,"onCreateResources");
        ManagerResources.prepareManager(this, getVertexBufferObjectManager());
        PhysicObjectWithSpriteDefinition.init(this);
        PhysicObjectWithSpriteLoader.init(this);
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
    	Logger.i(TAG,"onCreateScene");
        SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
    }

    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
    	Logger.i(TAG,"onPopulateScene");
        mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
            	Logger.i(TAG,"onPopulateScene | onTimePassed");
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
    
    @Override
    protected void onDestroy() {
    	Logger.i(TAG,"onDestroy");
    	super.onDestroy();
        System.exit(0);	
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
    	Logger.i(TAG,"onKeyDown | keyCode="+keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) { SceneManager.getInstance().getCurrentScene().onBackKeyPressed(); }
        if (keyCode == KeyEvent.KEYCODE_MENU) { SceneManager.getInstance().getCurrentScene().onMenuKeyPressed(); return true; }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) { SceneManager.getInstance().getCurrentScene().onVolUpKeyPressed(); return true; }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) { SceneManager.getInstance().getCurrentScene().onVolDownKeyPressed(); return true; }
        return false; 
    }
    
}