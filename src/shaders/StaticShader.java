package shaders;

public class StaticShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "src/shaders/vertexShader.glsl";
	public static final String FRAGMENT_FILE = "src/shaders/fragmentShader.glsl";
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	
	protected void bindAttributes() {
		super.bindAttribute(0, "position ");
		
	}
	
	

}
