package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import entities.Entity;
import entities.Light;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrains.Terrain;

public class MasterRenderer {
	private StaticShader shader;
	private EntityRenderer renderer;
	
	private Matrix4f projectionMatrix;
	
	private TerrainRenderer terrainRenderer;
	
	private TerrainShader terrainShader;
	
	private Map<TexturedMesh, List<Entity>> entities = new HashMap<>();
	private List<Terrain> terrains = new ArrayList<>();
	
	private float FOV = 70f;
	private float NEAR_PLANE = 0.1f;
	private float FAR_PLANE = 1000f;
	
	private static Matrix4f createProjectionMatrix(float FOV, float FAR_PLANE, float NEAR_PLANE) {
		float aspectRatio = (float) DisplayManager.getInstance().getWidth() / (float) DisplayManager.getInstance().getHeight();
		float y_scale = (float)(1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio;
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		Matrix4f toReturn = new Matrix4f();
		
		toReturn.m00(x_scale);
		toReturn.m11(y_scale);
		toReturn.m22(-( (FAR_PLANE + NEAR_PLANE) / frustum_length ));
		toReturn.m23(-1);
		toReturn.m32(-( (2 * NEAR_PLANE * FAR_PLANE) / frustum_length ));
		toReturn.m33(0);
		
		
		return toReturn;
		
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	
	public MasterRenderer() {
		this.shader = new StaticShader();
		
		this.projectionMatrix = createProjectionMatrix(FOV, FAR_PLANE, NEAR_PLANE);
		this.renderer = new EntityRenderer(shader, projectionMatrix);
		
		this.terrainShader = new TerrainShader();
		this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
	}
	
	public void render(Light sun, Camera cam) {
		prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(cam);
		renderer.render(entities);
		shader.stop();
		
		terrainShader.start();
		terrainShader.loadLight(sun);
		terrainShader.loadViewMatrix(cam);
		terrainRenderer.render(terrains);
		
		terrainShader.stop();
		
		terrains.clear();
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
	
	private void prepare() {
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	
	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
	}
}
