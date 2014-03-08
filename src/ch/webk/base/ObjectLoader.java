package ch.webk.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

public class ObjectLoader {
	
	private static final String TAG = "ObjectsLoader";
	
	public static BufferedReader bufferedReader;
	private static final String nameSpace = null;
	
	public static void init(Context context) {
		Logger.i(TAG,"init");
		ResourcesManager.getInstance().initGameTextures();
		for (InputStream objectDefinition : ObjectDefinitions.objectDefinitions) {
			try {
	            XmlPullParser parser = Xml.newPullParser();
	            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(objectDefinition, null);
	            String id = null;
	            String title = null;
            	String gfx = null;
            	boolean animated = false;
            	int x = 1;
            	int y = 1;
            	String vertex = "";
	            while (parser.next() != XmlPullParser.END_TAG) {
	            	if (parser.getEventType() != XmlPullParser.START_TAG) {
	                    continue;
	                }
	                String name = parser.getName();
	                if (name.equals("id")) {
	                	parser.require(XmlPullParser.START_TAG, nameSpace, "id");
	                	id = readText(parser);
	            	    parser.require(XmlPullParser.END_TAG, nameSpace, "id");
	                } else if (name.equals("title")) {
	                	parser.require(XmlPullParser.START_TAG, nameSpace, "title");
	                	title = readText(parser);
	            	    parser.require(XmlPullParser.END_TAG, nameSpace, "title");
	                } else if (name.equals("gfx")) {
	                	parser.require(XmlPullParser.START_TAG, nameSpace, "gfx");
	                	animated = Boolean.parseBoolean(parser.getAttributeValue(0));
	                    x = Integer.parseInt(parser.getAttributeValue(1));
	                    y = Integer.parseInt(parser.getAttributeValue(2));
	                	gfx = readText(parser);
	            	    parser.require(XmlPullParser.END_TAG, nameSpace, "gfx");
	                } else if (name.equals("vertex")) {
	                	parser.require(XmlPullParser.START_TAG, nameSpace, "vertex");
	                	vertex = readText(parser);
	            	    parser.require(XmlPullParser.END_TAG, nameSpace, "vertex");
	                }
	            }
	            Logger.i(TAG,"init | id="+id+", title="+title+", gfx="+gfx);
                ResourcesManager.getInstance().addGameTextures(id, title, gfx, animated, x, y, vertex);
	        } catch (Exception e) {
				Logger.e(TAG,"init | e="+e);
			} finally {
				Logger.i(TAG,"init | finally");
	        	try {objectDefinition.close();} catch (IOException e) {Logger.e(TAG,"init | finally | e="+e);}
	        }
		}
		ResourcesManager.getInstance().finalizeGameTextures();
	}
	
	private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	    	result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}

}