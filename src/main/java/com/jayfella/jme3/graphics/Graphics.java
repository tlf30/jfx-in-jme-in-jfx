package com.jayfella.jme3.graphics;

import com.jme3.app.Application;
import com.jme3.post.FilterPostProcessor;

public class Graphics {

    private static Graphics INSTANCE;

    private final Application app;

    private final FilterPostProcessor fpp;
    private final PostProcessor postProcessor;

    private final ContextSettings contextSettings;
    private final CameraSettings cameraSettings;

    private AnistropicFilterAssetListener anistropicListener;

    private Graphics(Application app) {
        this.app = app;

        fpp = new FilterPostProcessor(app.getAssetManager());
        app.getViewPort().addProcessor(fpp);
        postProcessor = new PostProcessor(fpp);

        contextSettings = new ContextSettings(app, fpp);
        cameraSettings = new CameraSettings(app);
    }

    /**
     * Initializes this graphics object.
     * @param application the jmonkey application.
     */
    public static void initialize(Application application) {
        INSTANCE = new Graphics(application);
    }

    public static Graphics getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the Post Processing methods
     * Deals with all things post-process based.
     * @return the post processor object.
     */
    public PostProcessor getPostProcessor() {
        return postProcessor;
    }

    /**
     * Gets the Context settings.
     * Details with all things context-based.
     * Any changes to the context must be followed up with getContextSettings.apply();
     * @return the context object.
     */
    public ContextSettings getContextSettings() {
        return contextSettings;
    }

    /**
     * Gets the camera settings.
     * Deals with all things camera-based.
     * @return The camers settings object.
     */
    public CameraSettings getCameraSettings() {
        return cameraSettings;
    }

    public void setAnistropicFilteringLevel(PowerLevel level) {

        if (level == PowerLevel.OFF) {
            if (anistropicListener != null) {
                app.getAssetManager().removeAssetEventListener(anistropicListener);
                anistropicListener = null;
            }
        }
        else {
            if (anistropicListener == null) {
                anistropicListener = new AnistropicFilterAssetListener(level.toInteger());
                app.getAssetManager().addAssetEventListener(anistropicListener);
            }
            else {
                anistropicListener.setLevel(level.toInteger());
            }
        }
    }

}
