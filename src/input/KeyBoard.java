package input;

import renderEngine.DisplayManager;
import static org.lwjgl.glfw.GLFW.*;

public class KeyBoard {
	
	public static boolean isKeyPressed(int key) {
		
		long window = DisplayManager.getInstance().getWindow();
		
		return glfwGetKey(window, key ) == GLFW_PRESS;
	}
}
