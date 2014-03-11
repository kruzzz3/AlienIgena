package ch.webk.base;

import java.io.IOException;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import ch.webk.base.factory.CustomJointFactory;
import ch.webk.base.manager.ManagerObject;
import ch.webk.base.manager.system.ManagerResources;
import ch.webk.base.object.PhysicObjectWithSprite;
import ch.webk.objects.Asteroid;
import ch.webk.objects.Banana;
import ch.webk.objects.Planet;
import ch.webk.objects.Spaceship;
import ch.webk.objects.Sun;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class LevelLoader {

	private static final String TAG = "LevelLoader";
	
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type"; // Typ
	private static final String TAG_ENTITY_ATTRIBUTE_ID = "id"; // ID
	private static final String TAG_ENTITY_ATTRIBUTE_UNIQUEID = "uniqueid"; // UniqueID
	private static final String TAG_ENTITY_ATTRIBUTE_BODYTYPE = "bodytype"; // UniqueID
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x"; // PosX
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y"; // PosY
	private static final String TAG_ENTITY_ATTRIBUTE_SX = "sx"; // ScaleX 0.1 - 2
	private static final String TAG_ENTITY_ATTRIBUTE_SY = "sy"; // ScaleY 0.1 - 2
	private static final String TAG_ENTITY_ATTRIBUTE_DENSITY = "d"; // Density
	private static final String TAG_ENTITY_ATTRIBUTE_ELASTICITY = "e"; // Elasticity
	private static final String TAG_ENTITY_ATTRIBUTE_FRICTION = "f"; // Friction

	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_ASTEROID = "asteroid";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SPACESHIP = "spaceship";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BANANA = "banana";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SUN = "sun";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLANET = "planet";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_DISJOINT = "disjoint";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_REVJOINT = "revjoint";
	
	public static void loadLevel(int levelID) {
    	Logger.i(TAG,"loadLevel | levelID="+levelID);
        final SimpleLevelLoader levelLoader = new SimpleLevelLoader(ManagerResources.getInstance().vbom);
        levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL) {
			@Override
			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pEntityLoaderData) throws IOException {
				Logger.i(TAG,"loadLevel | onLoadEntity | Screen");
				int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
				int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
				int walls = SAXUtils.getIntAttributeOrThrow(pAttributes, "walls");
				String bgId = SAXUtils.getAttributeOrThrow(pAttributes, "bg");
				
				ManagerObject.getCamera().setBounds(0, 0, width, height); // here we set camera bounds
				ManagerObject.getCamera().setBoundsEnabled(true);
                ManagerObject.getCamera().setZoomFactor(1.5f);
                
                Sprite bg = new Sprite(width / 2, height / 2, ManagerResources.getInstance().staticObjects.get(bgId), ManagerResources.getInstance().vbom);
                float sX = width / bg.getWidth();
                float sY = height / bg.getHeight();
                bg.setScale(sX, sY);
                ManagerObject.getGameScene().attachChild(bg);
                if (walls == 1) {
                	PhysicsFactory.createLineBody(ManagerObject.getPhysicsWorld(), 0, 0, 0, height, PhysicsFactory.createFixtureDef(1, 0, 1)).setUserData("wall");
                	PhysicsFactory.createLineBody(ManagerObject.getPhysicsWorld(), 0, height, width, height, PhysicsFactory.createFixtureDef(1, 0, 1)).setUserData("wall");
                	PhysicsFactory.createLineBody(ManagerObject.getPhysicsWorld(), width, height, width, 0, PhysicsFactory.createFixtureDef(1, 0, 1)).setUserData("wall");
                	PhysicsFactory.createLineBody(ManagerObject.getPhysicsWorld(), width, 0, 0, 0, PhysicsFactory.createFixtureDef(1, 0, 1)).setUserData("wall");
                }
                return ManagerObject.getGameScene();
			}
        });
        levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY) {
            public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException {
            	Logger.i(TAG,"loadLevel | onLoadEntity | Objects");
            	final String type = SAXUtils.getAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE, "");
            	final String id = SAXUtils.getAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_ID, "");
            	final String uniqueid = SAXUtils.getAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_UNIQUEID, null);
            	final String pType = SAXUtils.getAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_BODYTYPE, "");
            	
                final int x = SAXUtils.getIntAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_X, 0);
                final int y = SAXUtils.getIntAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_Y, 0);
                
                final float sx = SAXUtils.getFloatAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_SX, 1);
                final float sy = SAXUtils.getFloatAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_SY, 1);
                
                final float density = SAXUtils.getFloatAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_DENSITY, 0.5f);
                final float elasticity = SAXUtils.getFloatAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_ELASTICITY, 0.0f);
                final float friction = SAXUtils.getFloatAttribute(pAttributes, TAG_ENTITY_ATTRIBUTE_FRICTION, 0.5f);

                final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(density, elasticity, friction);
                
                BodyType bodyType = BodyType.DynamicBody;
                if (pType.equals("static"))
                {
                	bodyType = BodyType.StaticBody;
                }
                if (pType.equals("kinematic"))
                {
                	bodyType = BodyType.KinematicBody;
                }
                
                PhysicObjectWithSprite levelObject = null;
                if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_ASTEROID)) {
                	levelObject = new Asteroid(id, x, y, FIXTURE_DEF, bodyType, sx, sy);
                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BANANA)) {
                	levelObject = new Banana(id, x, y, FIXTURE_DEF, bodyType, sx, sy);
                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SUN)) {
                	Logger.i(TAG,"loadLevel | onLoadEntity | Objects | Sun");
                	levelObject = new Sun(id, x, y, FIXTURE_DEF, bodyType, sx, sy);
                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SPACESHIP)) {
                	Logger.i(TAG,"loadLevel | onLoadEntity | Objects | Spaceship");
                	levelObject = new Spaceship(id, x, y, FIXTURE_DEF, bodyType, sx, sy);
                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLANET)){
                	Logger.i(TAG,"loadLevel | onLoadEntity | Objects | Planet");
                	levelObject = new Planet(id, x, y, FIXTURE_DEF, bodyType, sx, sy);
                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_DISJOINT)){
                	Logger.i(TAG,"loadLevel | onLoadEntity | Objects | DisJoint");
                	String uniqueid1 = SAXUtils.getAttribute(pAttributes, "uniqueid1", null);
                	String uniqueid2 = SAXUtils.getAttribute(pAttributes, "uniqueid2", null);
                	CustomJointFactory.createDisJoint(uniqueid1, uniqueid2, 0, 0, 0);
                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_REVJOINT)) {
                	Logger.i(TAG,"loadLevel | onLoadEntity | Objects | RevJoint");
                	String uniqueid1 = SAXUtils.getAttribute(pAttributes, "uniqueid1", null);
                	String uniqueid2 = SAXUtils.getAttribute(pAttributes, "uniqueid2", null);
                	CustomJointFactory.createRevJointLimitless(uniqueid1, uniqueid2, 0, 0, true);
                }             
                if (levelObject != null) {
                	if (uniqueid != null && levelObject.getBody() != null) {
                		ManagerResources.getInstance().objectBody.put(uniqueid, levelObject.getBody());
                	}
                	levelObject.getSprite().setCullingEnabled(true);
                }
                return null;
            }
        });
        levelLoader.loadLevelFromAsset(ManagerResources.getInstance().activity.getAssets(), "level/" + levelID + ".lvl");
    }

}