package ch.webk.scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import ch.webk.base.manager.system.ManagerResources;
import ch.webk.scene.SceneManager.SceneType;

public class LoadingScene extends BaseScene {
	
	@Override
	public void createScene() {
	    setBackground(new Background(Color.BLACK));
	    attachChild(new Text(400, 240, ManagerResources.getInstance().fontBig, "Wird geladen...", ManagerResources.getInstance().vbom));
	}
	
	@Override
    public void disposeScene() {
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_LOADING;
    }

    @Override
    public void onBackKeyPressed() {
        return;
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