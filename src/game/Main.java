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
import renderEngine.Texture;
import renderEngine.TexturedMesh;
import terrains.Terrain;

public class Main {

	public static void main(String[] args) {
		DisplayManager manager = DisplayManager.getInstance();
		
		
		
		Camera cam =  new Camera();
		
		
		TexturedMesh testMesh = OBJLoader.loadObjModel("grassModel","flower");
		testMesh.getTexture().setTransparency(true);
//		testMesh.getTexture().setShineDamper(10);
//		testMesh.getTexture().setReflectivity(0.5f);
		
		
		Light light = new Light(new Vector3f(0,200,0),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,0,"grass");
		
		List<Entity> entities = new ArrayList<>();

		Random random = new Random();
	
		for(int i=0;i< 50; i++) {
			
			float x = random.nextFloat() * 100 - 50;
//			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * 100 - 50;
			
			
			entities.add(new Entity(testMesh,new Vector3f(x,0,z),0,0,0,0.5f));
		}
		
		
		
		
		MasterRenderer renderer = MasterRenderer.getInstance();
		
		manager.setWindowResizedCallback(new DisplayManager.WindowResized() {
			
			@Override
			public void windowResized() {
				System.out.println("Resized");
				renderer.updateProjectionMatrix();
			}
		});
		
		manager.setDraw(new GameLoopDraw() {
			
			@Override
			public void draw() {
				
				
				
				cam.move();
				
				for(Entity entity: entities) {
					
					renderer.processEntity(entity);
				}
				
				renderer.processTerrain(terrain);
				
				
				renderer.render(light, cam);
			}
		});
		
	}
	
	

}
