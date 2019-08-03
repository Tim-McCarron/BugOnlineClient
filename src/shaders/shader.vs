#version 120

attribute vec3 ant;
attribute vec2 ant_tex;

varying vec2 tex_coords;

uniform mat4 mvp;

void main() {
    tex_coords = ant_tex;
    gl_Position = mvp * vec4(ant, 1);
}