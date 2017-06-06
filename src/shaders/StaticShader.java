package shaders;

import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "src/shaders/vertexShader.glsl";
	public static final String FRAGMENT_FILE = "src/shaders/fragmentShader.glsl";
	
	private int location_transformationMatrix;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}


	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		System.out.println("Location: "+location_transformationMatrix);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	

}
