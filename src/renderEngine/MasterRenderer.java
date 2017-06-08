package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Entity;
import entities.Light;
import shaders.StaticShader;

public class MasterRenderer {
	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	private Map<TexturedMesh, List<Entity>> entities = new HashMap<>();
	
	
	public void render(Light sun, Camera cam) {
		renderer.prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(cam);
		renderer.render(entities);
		shader.stop();
		entities.clear(); // clear entities until the next frame.
	}
	
	public void processEntity(Entity entity) {
		TexturedMesh entityMesh = entity.getMesh();
		
		List<Entity> batch = entities.get(entityMesh);
		
		if(batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			
			newBatch.add(entity);
			
			entities.put(entityMesh, newBatch);
		}
	}
	
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
