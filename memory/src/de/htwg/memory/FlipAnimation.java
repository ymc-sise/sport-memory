package de.htwg.memory;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.graphics.Camera;
import android.graphics.Matrix;

/**
 * class to build an flip animation to turn the cards 
 * 
 * @author Simon Schneeberger <sischnee@gmail.com>
 * @version V1.0 18-01-2014
 * 
 */
public class FlipAnimation extends Animation {
    
	private float fromDeg;
    private float toDeg;
    private float centerX;
    private float centerY;
    private float depthZ;
	private Camera camera;


    /**
     * @param fromDeg flip start angle
     * @param toDeg flip end angle
     * @param centerX x position of the view object
     * @param centerY position of the view object
     */
    public FlipAnimation(float fromDeg, float toDeg, float centerX, float centerY) {
        this.fromDeg = fromDeg;
        this.toDeg = toDeg;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * @see android.view.animation.Animation#initialize(int, int, int, int)
     */
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera = new Camera();
    }

	/**
	 * @see android.view.animation.Animation#applyTransformation(float, android.view.animation.Transformation)
	 */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
    	
        float degrees = fromDeg + ((toDeg - fromDeg) * interpolatedTime);

        final Matrix matrix = t.getMatrix();

        camera.save();
        camera.translate(0.0f, 0.0f, depthZ * (1.0f - interpolatedTime));
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}