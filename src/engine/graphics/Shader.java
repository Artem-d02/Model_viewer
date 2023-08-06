package engine.graphics;

import engine.maths.Matrix4f;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.function.Function;

public class Shader {
    private interface ConvertableFArr2fArr {
        float[] convert(Float[] FArr);
    }
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
    }
    public int getUniformLocation(String name) {
        return GL20.glGetUniformLocation(programID, name);
    }
    public void setUniform(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }
    public void setUniform(String name, int value) {
        GL20.glUniform1i(getUniformLocation(name), value);
    }
    public void setUniform(String name, boolean value) {
        GL20.glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }
    public void setUniform(String name, Vector2f value) {
        GL20.glUniform2f(getUniformLocation(name), value.getX(), value.getY());
    }
    public void setUniform(String name, Vector3f value) {
        GL20.glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }
    public void setUniform(String name, Matrix4f value) {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
        ConvertableFArr2fArr FloatArr_to_floatArr_converter = (Float[] FArr) -> {
            float[] fArr = new float[FArr.length];
            int i = 0;
            for (Float f : FArr) {
                fArr[i++] = (f != null ? f : Float.NaN);
            }
            return fArr;
        };
        matrix.put(FloatArr_to_floatArr_converter.convert(value.toArray())).flip();
        GL20.glUniform4fv(getUniformLocation(name), matrix);
    }
    public void bind () {
        GL20.glUseProgram(programID);
    }
    public void unbind() {
        GL20.glUseProgram(0);
    }
    public void destroy() {
        GL20.glDetachShader(programID, vertexID);
        GL20.glDetachShader(programID, fragmentID);
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);

        GL20.glDeleteProgram(programID);
    }
}
