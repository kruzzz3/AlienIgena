package ch.webk.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import ch.webk.base.ObjectManager;
import ch.webk.base.ResourcesManager;
import ch.webk.scene.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

    private MenuScene menuChildScene;
    private final int MENU_START = 0;

    @Override
    public void createScene() {
        createBackground();
        createMenuChildScene();
    }

    @Override
    public void disposeScene() {
    }
    
    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_MENU;
    }

    private void createBackground() {
        attachChild(new Sprite(400, 240, ResourcesManager.getInstance().menu_background_region, ResourcesManager.getInstance().vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera)
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
    }

    private void createMenuChildScene() {
        menuChildScene = new MenuScene(ObjectManager.getCamera());
        menuChildScene.setPosition(0, 0);
        final IMenuItem startMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_START, ResourcesManager.getInstance().start_region, ResourcesManager.getInstance().vbom), 1.1f, 1);
        menuChildScene.addMenuItem(startMenuItem);
        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);
        startMenuItem.setPosition(startMenuItem.getX(), startMenuItem.getY());
        menuChildScene.setOnMenuItemClickListener(this);
        setChildScene(menuChildScene);
    }

    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch(pMenuItem.getID()) {
            case MENU_START:
            	SceneManager.getInstance().loadGameScene();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackKeyPressed() {
        System.exit(0);
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
