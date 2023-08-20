package engine.io;

import engine.graphics.Material;
import engine.graphics.Vertex;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import org.lwjgl.assimp.*;
import engine.graphics.Mesh;
import org.jetbrains.annotations.NotNull;

public class ModelLoader {
    public static Mesh loadModel(final @NotNull String filePath, final @NotNull String texturePath) throws IllegalStateException {
        AIScene scene = Assimp.aiImportFile(filePath, Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate);
        if (scene == null)
            throw new IllegalStateException("Error: couldn't load the model at " + filePath);
        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
        int vertexNumber = mesh.mNumVertices();

        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();

        Vertex[] vertexList = new Vertex[vertexNumber];

        for (int i = 0; i < vertexNumber; i++) {
            AIVector3D vertex = vertices.get(i);
            Vector3f meshVertex = new Vector3f(vertex.x(), vertex.y(), vertex.z());

            AIVector3D normal = normals.get(i);
            Vector3f meshNormal = new Vector3f(normal.x(), normal.y(), normal.z());

            Vector2f meshTextureCoords = new Vector2f(0, 0);
            if (mesh.mNumUVComponents().get(0) != 0) {
                AIVector3D texture = mesh.mTextureCoords(0).get(i);
                meshTextureCoords.set(texture.x(), texture.y());
            }

            vertexList[i] = new Vertex(meshVertex, meshNormal, meshTextureCoords);
        }

        int faceNumber = mesh.mNumFaces();
        AIFace.Buffer indices = mesh.mFaces();
        int[] indicesList = new int[faceNumber * 3];    //  because 3 vertices per triangle

        for (int i = 0; i < faceNumber; i++) {
            AIFace face = indices.get(i);
            indicesList[3 * i] = face.mIndices().get(0);
            indicesList[3 * i + 1] = face.mIndices().get(1);
            indicesList[3 * i + 2] = face.mIndices().get(2);
        }

        return new Mesh(vertexList, indicesList, new Material(texturePath));
    }
}
