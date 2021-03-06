package renderEngine;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class DisplayManager {
	
	public static interface WindowResized {
		public void windowResized();
	}
	
	private static DisplayManager Instance;
	
	
	
	public static DisplayManager getInstance() {
		if(Instance == null) {
			Instance = new DisplayManager(800, 600, "My 3d game");
		}
		
		return Instance;
	}
	
	private int WIDTH;
	private int HEIGHT;
	private final String TITLE;
	
	private WindowResized windowResizedCallback;
	
	
	
	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}
	
	private long window;
	public long getWindow() {
		return window;
	}
	
	
	public void setDraw(GameLoopDraw draw) {
		if(draw == null) {
			throw new IllegalArgumentException("draw cannot be null.");
		}
		
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		
		while(!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			draw.draw();
			
			glfwSwapBuffers(window);
		}
		
		glfwTerminate();
	}
	
	public void setWindowResizedCallback(WindowResized callback) {
		this.windowResizedCallback = callback;
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
		
		glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
			
			@Override
			public void invoke(long arg0, double arg1, double arg2) {
				System.out.println(arg1+","+arg2);
			}
		});
		
		
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		glfwSwapInterval(1);
		
		glfwSetWindowSizeCallback(window,new GLFWWindowSizeCallback() {
					
			@Override
			public void invoke(long arg0, int arg1, int arg2) {
				
				WIDTH = arg1;
				HEIGHT = arg2;
				GL11.glViewport(0, 0, WIDTH, HEIGHT);
				
				if(windowResizedCallback != null) {
					windowResizedCallback.windowResized();
				}
				
			}
		});
		
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		glfwSetWindowPos(window, (videoMode.width() - WIDTH)/2,(videoMode.height() - HEIGHT) / 2);
		
		glfwShowWindow(window);
		
		
	}
	
	
}
