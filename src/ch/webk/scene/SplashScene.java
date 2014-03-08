package ch.webk.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import ch.webk.base.ResourcesManager;
import ch.webk.scene.SceneManager.SceneType;

public class SplashScene extends BaseScene {

    private Sprite splash;

    @Override
    public void createScene() {
        splash = new Sprite(0, 0, ResourcesManager.getInstance().splash_region, ResourcesManager.getInstance().vbom)
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera)
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        splash.setPosition(400, 240);
        attachChild(splash);
    }
    
    @Override
    public void disposeScene() {
        splash.detachSelf();
        splash.dispose();
        this.detachSelf();
        this.dispose();
    }
    
    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneType.SCENE_SPLASH;
    }

    @Override
    public void onBackKeyPressed() {
    }

	@Override
	public void onMenuKeyPressed() {
	}

	@Override
	public void onVolUpKeyPressed() {
	}

	@Override
	public void onVolDownKeyPressed() {
	}

}