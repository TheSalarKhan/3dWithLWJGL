#version 130

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;


uniform mat4 transformationMatrix;

uniform mat4 projectionMatrix;

uniform mat4 viewMatrix;

uniform vec3 lightPosition;

uniform vec3 cameraPosition;

uniform float useFakeLighting;

void main(void) {
	
	vec3 actualNormal = normal;

	if(useFakeLighting > 0.5) {
		actualNormal = vec3(0.0,1.0,0.0);
	}
	
	vec4 worldPosition = transformationMatrix * vec4(position.x,position.y,position.z,1.0);

    gl_Position = projectionMatrix * viewMatrix * worldPosition;

    pass_textureCoords = textureCoords;
	
	surfaceNormal = (transformationMatrix * vec4(actualNormal,0)).xyz;
	toLightVector = lightPosition - worldPosition.xyz;

	
	
	toCameraVector = cameraPosition - worldPosition.xyz;
}
