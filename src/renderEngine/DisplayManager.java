package renderEngine;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class DisplayManager {
	
	
	private static DisplayManager Instance;
	
	
	
	public static DisplayManager getInstance() {
		if(Instance == null) {
			Instance = new DisplayManager(640, 480, "My 3d game");
		}
		
		return Instance;
	}
	
	private final int WIDTH;
	private final int HEIGHT;
	private final String TITLE;
	
	private final long window;
	
	
	public void setDraw(GameLoopDraw draw) {
		if(draw == null) {
			throw new IllegalArgumentException("draw cannot be null.");
		}
		
		
		while(!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			draw.draw();
			
			glfwSwapBuffers(window);
		}
		
		glfwTerminate();
	}

	private DisplayManager(int width, int height, String title) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.TITLE = title;
		
		if(!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		glfwWindowHint(GLFW_VISIBLE,  GLFW_FALSE);
		
		window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, 0, 0);
		
		if(window == 0) {
			throw new IllegalStateException("Failed to create window!");
		}
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		glfwSwapInterval(1);
		
		
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		glfwSetWindowPos(window, (videoMode.width() - WIDTH)/2,(videoMode.height() - HEIGHT) / 2);
		
		glfwShowWindow(window);
		
		
	}
	
	
}
