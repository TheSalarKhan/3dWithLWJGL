package renderEngine;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import shaders.TerrainShader;
import terrains.Terrain;
import toolbox.Maths;

public class TerrainRenderer {
	
	private TerrainShader shader;
	
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<Terrain> terrains) {
		for(Terrain terrain: terrains) {
			prepareTerrain(terrain);
			
			loadModelMatrix(terrain);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			
			unbindTexturedMesh();
		}
	}
	
	
	public void prepareTerrain(Terrain terrain) {
		glBindVertexArray(terrain.getMesh().getVAO());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glActiveTexture(GL_TEXTURE0);
		terrain.getMesh().getTexture().bind();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, terrain.getMesh().getIndicesVBO());
		
		shader.loadShineVariables(terrain.getMesh().getTexture().getShineDamper(), terrain.getMesh().getTexture().getReflectivity());
		
		
	}
	
	public void unbindTexturedMesh() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
	public void loadModelMatrix(Terrain terrain) {
		
		shader.loadTransformationMatrix( Maths.createTransformationMatrix(
				new Vector3f(terrain.getX(),0, terrain.getZ()), 0, 0, 0, 1));
	}

}
