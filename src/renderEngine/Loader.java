//package renderEngine;
//
//
//import java.nio.FloatBuffer;
//import java.nio.IntBuffer;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.lwjgl.BufferUtils;
//import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GL15;
//import org.lwjgl.opengl.GL20;
//import org.lwjgl.opengl.GL30;
//
//public class Loader {
//	private List<Integer> VAOs = new ArrayList<>();
//	private List<Integer> VBOs = new ArrayList<>();
//	
//	public Mesh loadToVAO(float[] vertices, int[] indices) {
//		int vaoId = createVAO();
//		
//		bindIndicesBuffer(indices);
//		bindVAO(vaoId);
//		
//		storeDataInAttribList(0, vertices);
//		
//		unbindVAO();
//		
//		return new Mesh(vaoId,indices.length);
//	}
//	
//	public void cleanUp() {
//		for(int vao: VAOs) {
//			GL30.glDeleteVertexArrays(vao);
//		}
//		
//		for(int vbo: VBOs) {
//			GL15.glDeleteBuffers(vbo);
//		}
//	}
//	
//	private void bindVAO(int vaoId) {
//		GL30.glBindVertexArray(vaoId);
//	}
//	
//	private IntBuffer storeDataInIntBuffer(int[] data) {
//		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
//		buffer.put(data);
//		buffer.flip();
//		return buffer;	
//	}
//	
//	private void bindIndicesBuffer(int[] indices) {
//		int vboId = GL15.glGenBuffers();
//		VBOs.add(vboId);
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
//		IntBuffer buffer = storeDataInIntBuffer(indices);
//		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
//	}
//	
//	
//	
//	private int createVAO() {
//		int vaoId = GL30.glGenVertexArrays();
//		VAOs.add(vaoId);
//		return vaoId;
//	}
//	
//	private void storeDataInAttribList(int attributeNumber, float[] data) {
//		int vboId = GL15.glGenBuffers();
//		VBOs.add(vboId);
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
//		FloatBuffer buffer = storeDataInFloatBuffer(data);
//		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
//		GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT , false, 0, 0);
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//	}
//	
//	private void unbindVAO() {
//		GL30.glBindVertexArray(0);
//	}
//	
//	private FloatBuffer storeDataInFloatBuffer(float[] data) {
//		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
//		buffer.put(data);
//		buffer.flip();
//		return buffer;
//	}
//}
