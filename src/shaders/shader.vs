#version 120

attribute vec3 vertices;
attribute vec2 textures;

varying vec2 tex_coords;

uniform mat4 mvp;

void main() {
    tex_coords = textures;
    gl_Position = mvp * vec4(vertices, 1);
}