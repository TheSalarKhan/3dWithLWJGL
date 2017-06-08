package renderEngine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import entities.Entity;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {
	
	private float FOV = 70f;
	private float NEAR_PLANE = 0.1f;
	private float FAR_PLANE = 1000f;
	
	private StaticShader shader;
	
	public Renderer(StaticShader shader) {
		this.shader = shader;
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
	
	public void prepare() {
		GL11.glClearColor(0.3f, 0f, 0.0f, 1);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Map<TexturedMesh, List<Entity>> entities) {
		for(TexturedMesh model: entities.keySet()) {
			
			prepareTexturedMesh(model);
			
			List<Entity> batch  = entities.get(model);
			
			for(Entity entity: batch) {
				prepareInstance(entity);
				
				glDrawElements(GL_TRIANGLES,model.getVertexCount(),GL_UNSIGNED_INT, 0);
			}
			
			unbindTexturedMesh();
		}
	}
	
	public void prepareTexturedMesh(TexturedMesh mesh) {
		glBindVertexArray(mesh.getVAO());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glActiveTexture(GL_TEXTURE0);
		mesh.getTexture().bind();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndicesVBO());
		
		shader.loadShineVariables(mesh.getTexture().getShineDamper(), mesh.getTexture().getReflectivity());
		
		
		
		
		
		
		
		
		
	}
	
	public void unbindTexturedMesh() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
	public void prepareInstance(Entity entity) {
		
		shader.loadTransformationMatrix(entity.getTransformationMatrix());
	}
	
	
}
