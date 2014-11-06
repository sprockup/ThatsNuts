package com.prockup.game.thatsnuts;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

public class GameActivity extends BaseGameActivity {
	// Control all resource allocation from a single class
	private ResourcesManager mcResourcesManager;
	
	// Camera values
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;	
	private Camera mcCamera;
		
	@Override
	public EngineOptions onCreateEngineOptions() {
		
		// Create the new camera
		mcCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		// Setup engine options for the engine object
		EngineOptions lcEngOpts = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				new FillResolutionPolicy(), mcCamera);
		
		lcEngOpts.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		
		// Return the created engine options	
		return lcEngOpts;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions acEngineOptions) {
		return new LimitedFPSEngine(acEngineOptions, 60);
	}
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback afOnCreateResourceCallback) {
		
		// Prepare the singleton Resources Manager class and keep a reference in this class
		ResourcesManager.prepareManager(mEngine, this, mcCamera, getVertexBufferObjectManager());
		mcResourcesManager = ResourcesManager.getInstance();
		
		// Notify the callback that we are finished loading necessary resources
		// in the game
		afOnCreateResourceCallback.onCreateResourcesFinished();
	}
	
	@Override
	public void onCreateScene(OnCreateSceneCallback afOnCreateResourceCallback) {
		SceneManager.getInstance().createSplashScene(afOnCreateResourceCallback);
	}
	
	@Override
	public void onPopulateScene(Scene arScene, 
			OnPopulateSceneCallback afOnCreateResourceCallback){
		// Setup splash scene
		mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() 
			{
				@Override
				public void onTimePassed(final TimerHandler pTimerHandler) {
					mEngine.unregisterUpdateHandler(pTimerHandler);
					SceneManager.getInstance().createMenuScene();
				}
			}));
				
		// Notify the callback that the scene population is complete
		afOnCreateResourceCallback.onPopulateSceneFinished();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode)
		{
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
	}
}
