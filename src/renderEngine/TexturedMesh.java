package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;



public class TexturedMesh {
	
	private int vao;
	private int verticesVBO;
	private int indicesVBO;
	private int texCoordsVBO;
	
	private int normalsVBO;
	
	private int vertexCount;
	
	private Texture texture;
	
	
	public Texture getTexture() {
		return texture;
	}
	
	public int getVAO() {
		return vao;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public int getIndicesVBO() {
		return indicesVBO;
	}

	public TexturedMesh(float[] vertices, int[] indices, float[] textureCoords, float[] normals , String imageName) {
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		storeVerticesInAttribList(0, vertices);
		storeTextureCoordsInAttributeList(1, textureCoords);
		storeNormalsInAttribList(2,normals);
		
		
		setupIndices(indices);
		
		vertexCount = indices.length;
		
		texture = new Texture(imageName);
		
		
		glBindVertexArray(0);
		
		
	}
	
	private void setupIndices(int indices[]) {
		
		indicesVBO = glGenBuffers();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	private void storeVerticesInAttribList(int attributeNumber, float[] vertices) {
		verticesVBO = glGenBuffers();
		
		storeFloatDataToVBO(attributeNumber, vertices, verticesVBO, 3);
	}
	
	private void storeTextureCoordsInAttributeList(int attributeNumber, float[] textureCoords) {
		texCoordsVBO = glGenBuffers();
		
		storeFloatDataToVBO(attributeNumber, textureCoords, texCoordsVBO,2);
	}
	
	private void storeNormalsInAttribList(int attributeNumber, float[] normals) {
		normalsVBO = glGenBuffers();
		
		storeFloatDataToVBO(attributeNumber, normals, normalsVBO, 3);
	}

	private void storeFloatDataToVBO(int attributeNumber, float[] data, int vbo, int size) {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT , false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	
	
	public void destroy() {
		glDeleteBuffers(verticesVBO);
		glDeleteBuffers(indicesVBO);
		glDeleteBuffers(texCoordsVBO);
		glDeleteTextures(texture.getId());
		glDeleteVertexArrays(vao);
	}
	
//	public void draw() {
//		
//		glBindVertexArray(vao);
//		glEnableVertexAttribArray(0);
//		glEnableVertexAttribArray(1);
//		glEnableVertexAttribArray(2);
//		
//		glActiveTexture(GL_TEXTURE0);
//		texture.bind();
//		
//		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
//		
//		glDrawElements(GL_TRIANGLES,vertexCount,GL_UNSIGNED_INT, 0);
//		
//		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
//		
//		
//		glDisableVertexAttribArray(2);
//		glDisableVertexAttribArray(1);
//		glDisableVertexAttribArray(0);
//		glBindVertexArray(0);
//	}
	
	
	
}
