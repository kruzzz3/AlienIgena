package ch.webk.scene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import ch.webk.base.Logger;
import ch.webk.base.ObjectManager;
import ch.webk.base.ResourcesManager;

public class SceneManager {
	
	private static final String TAG = "SceneManager";
	
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;

    private static final SceneManager INSTANCE = new SceneManager();
    private static SceneType currentSceneType = SceneType.SCENE_SPLASH;
    private static BaseScene currentScene;

    public enum SceneType {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
    }

    public void setScene(BaseScene scene) {
    	Logger.i(TAG,"setScene | BaseScene="+scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
        currentScene.resetCamera();
    }

    public void setScene(SceneType sceneType) {
        switch (sceneType) {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            default:
                break;
        }
    }

    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
    	ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    private void disposeSplashScene() {
    	ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }

    public void createMenuScene() {
    	ResourcesManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        ObjectManager.setScene(menuScene);
        SceneManager.getInstance().setScene(ObjectManager.getScene());
        disposeSplashScene();
    }
    
    public void loadMenuScene() {
    	ObjectManager.setScene(loadingScene);
        setScene(ObjectManager.getScene());
        gameScene.disposeScene();
        ResourcesManager.getInstance().unloadGameTextures();
        ObjectManager.getEngine().registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	ObjectManager.getEngine().unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuTextures();
                ObjectManager.setScene(menuScene);
                setScene(ObjectManager.getScene());
            }
        }));
    }

    public void loadGameScene() {
    	ObjectManager.setScene(loadingScene);
        setScene(ObjectManager.getScene());
        ResourcesManager.getInstance().unloadMenuTextures();
        ObjectManager.getEngine().registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	ObjectManager.getEngine().unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameTextures();
                gameScene = new GameScene();
                ObjectManager.setScene(gameScene);
                setScene(ObjectManager.getScene());
            }
        }));
    }
    
    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public SceneType getCurrentSceneType() {
        return currentSceneType;
    }

    public BaseScene getCurrentScene(){
        return currentScene;
    }

}