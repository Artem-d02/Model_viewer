#version 460 core

in vec3 position;
in vec3 color;
in vec2 textureCoords;

out vec3 passColor;
out vec2 passTextureCoords;

uniform mat4 model;

void main() {
    gl_Position = model * vec4(position, 1.0);
    passColor = color;
    passTextureCoords = textureCoords;
}