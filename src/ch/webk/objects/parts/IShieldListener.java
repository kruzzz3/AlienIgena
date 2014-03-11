package ch.webk.objects.parts;

import org.andengine.entity.sprite.AnimatedSprite;

public interface IShieldListener {

	public void onShieldPointsCreated(AnimatedSprite shieldPoints);
	public void onShieldIsEmpty();
	public void onShieldHasCapacity();
	
}
