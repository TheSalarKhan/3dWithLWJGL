package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


public class Mesh {
	private int vao;
	private int verticesVBO;
	private int indicesVBO;
	
	private int vertexCount;
	
	public Mesh(float vertices[], int indices[]) {
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		storeVerticesInAttribList(0, vertices);
		setupIndices(indices);
		
		
		glBindVertexArray(0);
		
		vertexCount = indices.length;
	}
	
	private void setupIndices(int indices[]) {
		
		indicesVBO = glGenBuffers();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	private void storeVerticesInAttribList(int attributeNumber, float[] vertices) {
		verticesVBO = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, verticesVBO);
		
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, 3, GL_FLOAT , false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void destroy() {
		glDeleteBuffers(verticesVBO);
		glDeleteBuffers(indicesVBO);
		glDeleteVertexArrays(vao);
	}
	
	public void draw() {
		
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
		
		glDrawElements(GL_TRIANGLES,vertexCount,GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
}
