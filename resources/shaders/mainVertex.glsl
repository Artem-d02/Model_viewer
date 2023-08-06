#version 460 core

in vec3 position;
in vec3 color;
in vec2 textureCoords;

out vec3 passColor;
out vec2 passTextureCoords;

uniform float scale;

void main() {
    gl_Position = vec4(position, 1.0) * vec4(scale, scale, scale, 1.0);
    passColor = color;
    passTextureCoords = textureCoords;
}