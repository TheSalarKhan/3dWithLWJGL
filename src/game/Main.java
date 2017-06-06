package game;

import org.joml.Vector3f;

import entities.Entity;
import renderEngine.DisplayManager;
import renderEngine.GameLoopDraw;
import renderEngine.TexturedMesh;
import shaders.StaticShader;

public class Main {

	public static void main(String[] args) {
		DisplayManager manager = DisplayManager.getInstance();
		
		StaticShader shader = new StaticShader();
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f,
				
		};
		
		int[] indices = {
				0,1,3,
				3,1,2
		};
		
		float[] textureCords = {
				0,0,
				0,1,
				1,1,
				1,0
		};
		
		TexturedMesh testMesh = new TexturedMesh(vertices, indices, textureCords, "texture");
		
		Entity entity = new Entity(testMesh,new Vector3f(0,0,0),0,0,0,1, shader);
		
		manager.setDraw(new GameLoopDraw() {
			
			@Override
			public void draw() {
				
				entity.draw();
				
			}
		});
	}

}
