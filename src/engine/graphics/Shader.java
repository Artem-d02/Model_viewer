package engine.graphics;

import engine.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {
    private String vertexFile;
    private String fragmentFile;
    private int vertexID;
    private int fragmentID;
    private int programID;
    public Shader(String vertexPath, String fragmentPath) {
        vertexFile = FileUtils.loadAsString(vertexPath);
        fragmentFile = FileUtils.loadAsString(fragmentPath);
    }
    public void create() throws IllegalStateException {
        programID = GL20.glCreateProgram();
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

        GL20.glShaderSource(vertexID, vertexFile);
        GL20.glCompileShader(vertexID);

        if (GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Vertex Shader: " + GL20.glGetShaderInfoLog(vertexID));
            throw new IllegalStateException("Error: vertex shader was not compiled");
        }

        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        GL20.glShaderSource(fragmentID, fragmentFile);
        GL20.glCompileShader(fragmentID);

        if (GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Fragment Shader: " + GL20.glGetShaderInfoLog(fragmentID));
            throw new IllegalStateException("Error: fragment shader was not compiled");
        }

        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.err.println("Program Linking: " + GL20.glGetProgramInfoLog(programID));
            throw new IllegalStateException("Error: shader program was not linked");
        }

        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Program Validation: " + GL20.glGetProgramInfoLog(programID));
            throw new IllegalStateException("Error: shader program doesn't pass validation");
        }

        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
    }
    public void bind () {
        GL20.glUseProgram(programID);
    }
    public void unbind() {
        GL20.glUseProgram(0);
    }
    public void destroy() {
        GL20.glDeleteProgram(programID);
    }
}
