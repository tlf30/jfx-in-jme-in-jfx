package com.jayfella.jme3.graphics;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;

/**
 * Sets camera view frustums and field of view correctly using convenience methods.
 * The camera settings allow changing each individual field, however changing some settings causes undesired behavior.
 * This convenience class simply lets you change the required settings in an easier manner.
 */
public class CameraSettings {

    private final Application app;
    private final Camera cam;

    private float fov = 45; // the default JME FOV

    CameraSettings(Application app) {
        this.app = app;
        this.cam = app.getCamera();
    }

    public float getAspectRatio() {
        return (float)cam.getWidth() / (float)cam.getHeight();
    }

    public float getFrustumNear() {
        return cam.getFrustumNear();
    }

    public void setFrustumNear(float near) {
        cam.setFrustumPerspective( fov, getAspectRatio(), near, cam.getFrustumFar());
    }

    public float getFrustumFar() {
        return cam.getFrustumFar();
    }

    public void setFrustumFar(float far) {
        float aspectRatio = (float)cam.getWidth() / (float)cam.getHeight();
        cam.setFrustumPerspective( fov, getAspectRatio(), cam.getFrustumNear(), far);
    }

    public float getFieldOfView() {
        return this.fov;
    }

    public void setFieldOfView(float fov) {
        this.fov = fov;
        cam.setFrustumPerspective( fov, getAspectRatio(), cam.getFrustumNear(), cam.getFrustumFar());
    }

    /**
     * Set the near frustum, far frustum and field of view.
     * @param near the near view frustum.
     * @param far  the far view frustum.
     * @param fov  the field of view.
     */
    public void set(float near, float far, float fov) {
        this.fov = fov;
        cam.setFrustumPerspective( fov, getAspectRatio(), near, far);
    }

    private Texture2D deptTexture;

    /**
     * Lazily initializes and returns the depth texture.
     * @return the depth texture of the main camera.
     */
    public Texture2D getDepthTexture() {

        if (deptTexture == null) {
            FrameBuffer depthBuffer = new FrameBuffer(app.getCamera().getWidth(), app.getCamera().getHeight(), 0);
            depthBuffer.setDepthBuffer(Image.Format.Depth);
            deptTexture = new Texture2D(depthBuffer.getWidth(), depthBuffer.getHeight(), Image.Format.Depth);
            depthBuffer.setDepthTexture(deptTexture);

            ViewPort depthView = app.getRenderManager().createPreView("depthView", app.getCamera());
            depthView.setOutputFrameBuffer(depthBuffer);
            depthView.setClearDepth(true);
            depthView.attachScene(((SimpleApplication) app).getRootNode());
        }

        return deptTexture;
    }

}
