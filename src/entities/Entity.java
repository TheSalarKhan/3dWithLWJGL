package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import renderEngine.TexturedMesh;
import shaders.StaticShader;
import toolbox.Maths;


public class Entity {
	private TexturedMesh mesh;
	private Vector3f position;
	private float rx,ry,rz;
	private float scale;
	private StaticShader shader;
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rx += dx;
		this.ry += dy;
		this.rz += dz;
	}
	
	private void uploadAllUniforms() {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(position, rx, ry, rz, scale);
		shader.loadTransformationMatrix(transformationMatrix);
		
	}
	
	public void draw() {
		shader.start();
		uploadAllUniforms();
		mesh.draw();
		shader.stop();
	}
	
	public TexturedMesh getMesh() {
		return mesh;
	}

	public void setMesh(TexturedMesh mesh) {
		this.mesh = mesh;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Entity(TexturedMesh mesh, Vector3f position, float rx, float ry, float rz, float scale, StaticShader shader) {
		super();
		this.mesh = mesh;
		this.position = position;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		this.scale = scale;
		this.shader = shader;
	}
	
	
	
}
