package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;

import entities.Entity;
import entities.Light;
import renderEngine.Camera;
import renderEngine.DisplayManager;
import renderEngine.GameLoopDraw;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.TexturedMesh;

public class Main {

	public static void main(String[] args) {
		DisplayManager manager = DisplayManager.getInstance();
		
//		StaticShader shader = new StaticShader();
		
		
		
		Camera cam =  new Camera();
		
		
		TexturedMesh testMesh = OBJLoader.loadObjModel("f16");
		testMesh.getTexture().setShineDamper(10);
		testMesh.getTexture().setReflectivity(0.5f);
		
		
		Light light = new Light(new Vector3f(0,0,1),new Vector3f(1,1,1));
		
		
		
		List<Entity> planes = new ArrayList<>();
		
		
		Random random = new Random();
		
		
		for(int i=0;i< 500; i++) {
			
			float x = random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * 100 - 50;
			
			
			planes.add(new Entity(testMesh,new Vector3f(x,y,z),0,0,0,0.5f));
		}
		
		
		
		
		MasterRenderer renderer = new MasterRenderer();
		
		manager.setDraw(new GameLoopDraw() {
			
			@Override
			public void draw() {
				
				
				
				cam.move();
				
				for(Entity entity: planes) {
					
					entity.increaseRotation(1, 0, 0);
					renderer.processEntity(entity);
				}
				
				
				
				renderer.render(light, cam);
//				entity.increasePosition(0.002f, 0, -0.01f);
//				
				
//				shader.start();
//				shader.loadLight(light);
//				shader.loadShineVariables(testMesh.getTexture().getShineDamper(), testMesh.getTexture().getReflectivity());
//				shader.loadViewMatrix(cam);
//				entity.draw();
				
			}
		});
		
	}
	
	

}
