package ch.webk.scene;

import org.andengine.entity.scene.Scene;
import ch.webk.base.ObjectManager;
import ch.webk.scene.SceneManager.SceneType;

public abstract class BaseScene extends Scene {

    public BaseScene() {
        createScene();
    }
    
    public void resetCamera() {
    	ObjectManager.getCamera().reset();
    	ObjectManager.getCamera().setChaseEntity(null);
    	ObjectManager.getCamera().setHUD(null);
    	ObjectManager.getCamera().setCenter(400, 240);
    	ObjectManager.getCamera().setZoomFactor(1);
    	ObjectManager.getCamera().setBounds(0, 0, 800, 480);
    }
    
    
    public abstract void createScene();
    public abstract void disposeScene();
    public abstract SceneType getSceneType();
    public abstract void onBackKeyPressed();
    public abstract void onMenuKeyPressed();
    public abstract void onVolUpKeyPressed();
    public abstract void onVolDownKeyPressed();

}