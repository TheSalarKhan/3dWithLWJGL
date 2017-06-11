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

public class EntityRenderer {
	
	
	
	private StaticShader shader;
	
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
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
		
		if(mesh.getTexture().hasTransparency()) {
			MasterRenderer.disableCulling();
		}
		
		shader.loadFakeLightingVariable(mesh.getTexture().hasFakeLighting());
		
		mesh.getTexture().bind();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndicesVBO());
		
		shader.loadShineVariables(mesh.getTexture().getShineDamper(), mesh.getTexture().getReflectivity());
		
		
	}
	
	public void unbindTexturedMesh() {
		
		MasterRenderer.enableCulling();
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
