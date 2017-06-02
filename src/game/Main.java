package game;

import renderEngine.DisplayManager;
import renderEngine.GameLoopDraw;
import renderEngine.Mesh;
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
		
		Mesh testMesh = new Mesh(vertices, indices);
		
		manager.setDraw(new GameLoopDraw() {
			
			@Override
			public void draw() {
				
				shader.start();
				testMesh.draw();
				shader.stop();
				
			}
		});
	}

}
