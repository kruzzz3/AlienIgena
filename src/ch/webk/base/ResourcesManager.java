package ch.webk.base;

import java.util.HashMap;
import java.util.Map;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Color;

import com.badlogic.gdx.physics.box2d.Body;

public class ResourcesManager {

	private static final String TAG = "SceneManager";
	
    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public GameActivity activity;
    public VertexBufferObjectManager vbom;
    
    public Font fontSmall;
    public Font fontMedium;
    public Font fontBig;
    
    public ITextureRegion splash_region;
    private BitmapTextureAtlas splashTextureAtlas;

    public ITextureRegion menu_background_region;
    public ITextureRegion start_region;
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    
    public BuildableBitmapTextureAtlas staticGameTextureAtlas;
    public Map<String,ITextureRegion> staticObjects;
    public BuildableBitmapTextureAtlas animatedGameTextureAtlas;
    public Map<String,ITiledTextureRegion> animatedObjects; 
   
    
    //---------------------------------------------
    // SYSTEM
    //---------------------------------------------
    
    public Map<String,Body> objectBody;
    public Map<String,String> objectsVertex;
    public Map<String,String> objectsTitle;
    
    public static void prepareManager(GameActivity activity, VertexBufferObjectManager vbom)
    {
        getInstance().activity = activity;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // LOAD & UNLOAD --> ON THE FLY
    //---------------------------------------------
    
    public void loadMenuTextures() { menuTextureAtlas.load(); } 
    public void unloadMenuTextures() { menuTextureAtlas.unload(); }
    
    public void loadGameTextures() {
    	staticGameTextureAtlas.load();
    	animatedGameTextureAtlas.load();
    }
    
    public void unloadGameTextures() {
    	staticGameTextureAtlas.unload();
    	animatedGameTextureAtlas.unload();
    }
    
    //---------------------------------------------
    // SPLASH
    //---------------------------------------------

    public void loadSplashScreen() {
    	loadFonts();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.jpg", 0, 0);
        splashTextureAtlas.load();
    }

    public void unloadSplashScreen() {
        splashTextureAtlas.unload();
        splash_region = null;
    }
    
    //---------------------------------------------
    // FONT
    //---------------------------------------------
    
    private void loadFonts() {
        FontFactory.setAssetBasePath("font/");
        
        final ITexture mainFontTextureBig = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        fontBig = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTextureBig, activity.getAssets(), "Ubuntu-L.ttf", 30, true, Color.BLACK, 2, Color.WHITE);
        fontBig.load();
        
        final ITexture mainFontTextureMedium = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        fontMedium = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTextureMedium, activity.getAssets(), "Ubuntu-L.ttf", 20, true, Color.BLACK, 2, Color.WHITE);
        fontMedium.load();
        
        final ITexture mainFontTextureSmall = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);  
        fontSmall = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTextureSmall, activity.getAssets(), "Ubuntu-L.ttf", 15, true, Color.BLACK, 2, Color.WHITE);
        fontSmall.load();
    }
    
    //---------------------------------------------
    // MENU
    //---------------------------------------------
    
    public void loadMenuResources() {
    	loadMenuGraphics();
    }
    
    private void loadMenuGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.jpg");
        start_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "start.png");
        try {
            this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.menuTextureAtlas.load();
        } catch (final TextureAtlasBuilderException e) { Logger.e(TAG,"loadMenuGraphics | menuTextureAtlas | e="+e); }
    }
   
    //---------------------------------------------------------------------------------------------------------------------------------------
    // GameObjects
    //---------------------------------------------------------------------------------------------------------------------------------------
    
    public void initGameTextures() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
    	animatedObjects = null; animatedObjects = new HashMap<String,ITiledTextureRegion>();
    	animatedGameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
    	staticObjects = null; staticObjects = new HashMap<String,ITextureRegion>();
    	staticGameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
    	objectBody = new HashMap<String, Body>();
    	objectsVertex = null; objectsVertex = new HashMap<String, String>();
    	objectsTitle = null; objectsTitle = new HashMap<String, String>();
    }
    
    public void addGameTextures(String id, String title, String gfx, boolean animated, int x, int y, String vertex) {
    	if (animated) { animatedObjects.put(id, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedGameTextureAtlas, activity, gfx, x, y)); }
    	else { staticObjects.put(id, BitmapTextureAtlasTextureRegionFactory.createFromAsset(staticGameTextureAtlas, activity, gfx)); }
    	objectsTitle.put(id, title);
    	objectsVertex.put(id, vertex);
    }
    
    public void finalizeGameTextures() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
    	try  {
            this.staticGameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.staticGameTextureAtlas.load();
        } catch (final TextureAtlasBuilderException e) { Logger.e(TAG,"finalizeGameTextures | staticGameTextureAtlas | e="+e); }
    	try {
            this.animatedGameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.animatedGameTextureAtlas.load();
        } catch (final TextureAtlasBuilderException e) {  Logger.e(TAG,"finalizeGameTextures | animatedGameTextureAtlas | e="+e); }
    }

    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }
    
    public VertexBufferObjectManager getVertexBufferObjectManager() {
    	return vbom;
    }

}