package renderEngine;

import org.joml.Matrix4f;

import shaders.StaticShader;

public class Renderer {
	
	private float FOV = 70f;
	private float NEAR_PLANE = 0.1f;
	private float FAR_PLANE = 1000f;
	
	private StaticShader shader;
	
	public Renderer(StaticShader shader) {
		this.shader = shader;
		this.init();
	}
	private void init() {
		Matrix4f projectionMatrix = createProjectionMatrix(FOV, FAR_PLANE, NEAR_PLANE); 
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	private static Matrix4f createProjectionMatrix(float FOV, float FAR_PLANE, float NEAR_PLANE) {
		float aspectRatio = (float) DisplayManager.getInstance().getWidth() / (float) DisplayManager.getInstance().getHeight();
		float y_scale = (float)(1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio;
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		Matrix4f toReturn = new Matrix4f();
		
		toReturn.m00(x_scale);
		toReturn.m11(y_scale);
		toReturn.m22(-( (FAR_PLANE + NEAR_PLANE) / frustum_length ));
		toReturn.m23(-1);
		toReturn.m32(-( (2 * NEAR_PLANE * FAR_PLANE) / frustum_length ));
		toReturn.m33(0);
		
		
		return toReturn;
		
	}
}
