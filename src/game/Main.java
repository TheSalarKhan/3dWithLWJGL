package game;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import entities.Entity;
import entities.Light;
import renderEngine.Camera;
import renderEngine.DisplayManager;
import renderEngine.GameLoopDraw;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import renderEngine.TexturedMesh;
import shaders.StaticShader;

public class Main {

	public static void main(String[] args) {
		DisplayManager manager = DisplayManager.getInstance();
		
		StaticShader shader = new StaticShader();
		
		Renderer renderer = new Renderer(shader);
		
		Camera cam =  new Camera();
		
		
		TexturedMesh testMesh = OBJLoader.loadObjModel("f16");
		
		testMesh.getTexture().setShineDamper(5);
		testMesh.getTexture().setReflectivity(1);
		
		
		Entity entity = new Entity(testMesh,new Vector3f(0,0,-2),0,0,0,0.5f, shader);
		
		Light light = new Light(new Vector3f(0,0,1),new Vector3f(1,1,1));
		
		
		
		
		
		
		
		manager.setDraw(new GameLoopDraw() {
			
			@Override
			public void draw() {
//				entity.increasePosition(0.002f, 0, -0.01f);
				entity.increaseRotation(0, 1, 2);
				cam.move();
				shader.start();
				shader.loadLight(light);
				shader.loadShineVariables(testMesh.getTexture().getShineDamper(), testMesh.getTexture().getReflectivity());
				shader.loadViewMatrix(cam);
				entity.draw();
				
			}
		});
	}
	
	

}
