package ch.webk.base;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;

public class AnimationManager {

	public static void startAnimationNormal(AnimatedSprite animatedSprite, int frameDurations, boolean loop, IAnimationListener animationListener) {
		animatedSprite.animate(frameDurations, loop, animationListener);
	}
	
}
