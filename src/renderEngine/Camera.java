package renderEngine;

import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

import input.KeyBoard;

public class Camera {
	private Vector3f position = new Vector3f(0,0,0);
	private float roll;
	private float pitch;
	private float yaw;
	
	public void move() {
		if(KeyBoard.isKeyPressed(GLFW_KEY_W)) {
			position.z -= 0.02f;
		}
		if(KeyBoard.isKeyPressed(GLFW_KEY_S)) {
			position.z += 0.02f;
		}
		if(KeyBoard.isKeyPressed(GLFW_KEY_D)) {
			position.x += 0.02f;
		}
		if(KeyBoard.isKeyPressed(GLFW_KEY_A)) {
			position.x -= 0.02f;
		}
	}
	
	public Camera() {
		
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
}
