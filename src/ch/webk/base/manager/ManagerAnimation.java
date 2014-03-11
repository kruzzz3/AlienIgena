package ch.webk.base.manager;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;

public class ManagerAnimation {

	public static void startAnimationNormal(AnimatedSprite animatedSprite, int frameDurations, boolean loop, IAnimationListener animationListener) {
		animatedSprite.animate(frameDurations, loop, animationListener);
	}
	
	public static void stepToFrame(AnimatedSprite animatedSprite, int frame) {
		int maxTile = animatedSprite.getTileCount() - 1;
		if (frame > maxTile) {
			frame = maxTile;
		} else if(frame < 0) {
			frame = 0;
		}
		animatedSprite.setCurrentTileIndex(frame);
	}
	
}
