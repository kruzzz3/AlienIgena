package ch.webk.scene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import ch.webk.base.Logger;
import ch.webk.base.manager.ManagerObject;
import ch.webk.base.manager.system.ManagerResources;

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
        ManagerObject.getEngine().setScene(scene);
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
    	ManagerResources.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    private void disposeSplashScene() {
    	ManagerResources.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }

    public void createMenuScene() {
    	ManagerResources.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        SceneManager.getInstance().setScene(menuScene);
        disposeSplashScene();
    }
    
    public void loadMenuScene() {
        setScene(loadingScene);
        gameScene.disposeScene();
        ManagerResources.getInstance().unloadGameTextures();
        ManagerObject.getEngine().registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	ManagerObject.getEngine().unregisterUpdateHandler(pTimerHandler);
                ManagerResources.getInstance().loadMenuTextures();
                setScene(menuScene);
            }
        }));
    }

    public void loadGameScene() {
        setScene(loadingScene);
        ManagerResources.getInstance().unloadMenuTextures();
        ManagerObject.getEngine().registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	ManagerObject.getEngine().unregisterUpdateHandler(pTimerHandler);
                ManagerResources.getInstance().loadGameTextures();
                gameScene = new GameScene();
                setScene(gameScene);
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