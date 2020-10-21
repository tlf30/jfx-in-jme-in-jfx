package com.jayfella.jme3.graphics;

import com.jme3.app.Application;
import com.jme3.post.FilterPostProcessor;
import com.jme3.system.JmeContext;

public class ContextSettings {

    private final Application app;
    private final JmeContext jmeContext;
    private final FilterPostProcessor fpp;

    ContextSettings(Application app, FilterPostProcessor fpp) {
        this.app = app;
        this.jmeContext = app.getContext();
        this.fpp = fpp;
    }

    public int getFrameRate() { return jmeContext.getSettings().getFrameRate(); }
    public void setFrameRate(int frameRate) { jmeContext.getSettings().setFrameRate(frameRate); }

    public boolean isGammaCorrection() { return jmeContext.getSettings().isGammaCorrection(); }
    public void setGammaCorrection(boolean gammaCorrection) { jmeContext.getSettings().setGammaCorrection(gammaCorrection); }

    public int getResolutionWidth() { return app.getViewPort().getCamera().getWidth(); }
    public int getResolutionHeight() { return app.getViewPort().getCamera().getHeight(); }
    public void setResolution(int width, int height) { jmeContext.getSettings().setResolution(width, height); }

    public boolean isFullScreen() { return jmeContext.getSettings().isFullscreen(); }
    public void setFullScreen() { jmeContext.getSettings().setFullscreen(true); }

    public boolean isVsync() { return jmeContext.getSettings().isVSync(); }
    public void setVsync(boolean vsync) { jmeContext.getSettings().setVSync(true); }

    public void setNumSamples(PowerLevel powerLevel) {
        fpp.setNumSamples(powerLevel.toInteger());
        app.getContext().getSettings().setSamples(powerLevel.toInteger());
    }

    public void apply() {
        jmeContext.restart();
    }

}
