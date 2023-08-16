package engine.graphics;

import engine.maths.Vector2f;
import engine.maths.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
    private Vertex[] vertices;
    private int[] indices;
    private Material material;
    private int vao;
    private int pbo;
    private int cbo;
    private int tbo;
    private int ibo;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Mesh(Vertex[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    public void create() {
        material.create();

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * Vector3f.size());
        float[] positionData = new float[vertices.length * Vector3f.size()];
        for (int i = 0; i < vertices.length; i++) {
            positionData[3 * i] = vertices[i].getPosition().getX();
            positionData[3 * i + 1] = vertices[i].getPosition().getY();
            positionData[3 * i + 2] = vertices[i].getPosition().getZ();
        }
        positionBuffer.put(positionData).flip();

        pbo = storeData(positionBuffer, 0, Vector3f.size());

        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * Vector3f.size());
        float[] colorData = new float[vertices.length * Vector3f.size()];
        for (int i = 0; i < vertices.length; i++) {
            colorData[3 * i] = vertices[i].getColor().getX();
            colorData[3 * i + 1] = vertices[i].getColor().getY();
            colorData[3 * i + 2] = vertices[i].getColor().getZ();
        }
        colorBuffer.put(colorData).flip();

        cbo = storeData(colorBuffer, 1, Vector3f.size());

        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * Vector2f.size());
        float[] textureData = new float[vertices.length * Vector2f.size()];
        for (int i = 0; i < vertices.length; i++) {
            textureData[2 * i] = vertices[i].getTextureCoords().getX();
            textureData[2 * i + 1] = vertices[i].getTextureCoords().getY();
        }
        textureBuffer.put(textureData).flip();

        tbo = storeData(textureBuffer, 2, Vector2f.size());

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();

        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    public void destroy() {
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(cbo);
        GL15.glDeleteBuffers(ibo);
        GL15.glDeleteBuffers(tbo);

        GL30.glDeleteVertexArrays(vao);

        material.destroy();
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
    public int getVAO() {
        return vao;
    }
    public int getPBO() {
        return pbo;
    }
    public int getCBO() {
        return cbo;
    }
    public int getTBO() { return tbo; }
    public int getIBO() {
        return ibo;
    }
    public Material getMaterial() {
        return material;
    }
}
