package ch.webk.scene;

import org.andengine.entity.scene.Scene;

import ch.webk.base.manager.ManagerObject;
import ch.webk.scene.SceneManager.SceneType;

public abstract class BaseScene extends Scene {

    public BaseScene() {
        createScene();
    }
    
    public void resetCamera() {
    	ManagerObject.getCamera().reset();
    	ManagerObject.getCamera().setChaseEntity(null);
    	ManagerObject.getCamera().setHUD(null);
    	ManagerObject.getCamera().setCenter(400, 240);
    	ManagerObject.getCamera().setZoomFactor(1);
    	ManagerObject.getCamera().setBounds(0, 0, 800, 480);
    }
    
    
    public abstract void createScene();
    public abstract void disposeScene();
    public abstract SceneType getSceneType();
    public abstract void onBackKeyPressed();
    public abstract void onMenuKeyPressed();
    public abstract void onVolUpKeyPressed();
    public abstract void onVolDownKeyPressed();

}