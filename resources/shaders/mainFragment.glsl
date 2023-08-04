#version 330 core

in vec3 color;
varying out vec4 outColor;

void main() {
    outColor = vec4(color, 0.5);
}