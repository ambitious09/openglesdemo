package com.example.opengldemo;

import static android.opengl.GLES20.*;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GlRegender implements GLSurfaceView.Renderer {
     private FloatBuffer vertexBuffer;
     private  final  String vertexShader= "attribute vec4 vPosition;"+
             "void main(){"+
             "gl_Position=vPosition;"+
             "}";
    private final String fragmentShaderCode=
            "precision mediump float;"+
                    "uniform vec4 vColor;"+
                    "void main(){"+
                    "   gl_FragColor=vColor;"+
                    "}";

    /**
     * void：空类型，即不返回任何值
     * bool：布尔类型，true/false
     * int：带符号的整数，signed integer
     * float：带符号的浮点数，signed scalar
     * vec2、vec3、vec4：n-维浮点数向量
     * bvec2、bvec3、bvec4：n-维布尔向量
     * ivec2、ivec3、ivec4：n-维整数向量
     * mat2、mat3、mat4：2x2、3x3、4x4 浮点数矩阵
     * sampler2D：2D 纹理
     * samplerCube：盒纹理
     *
     *
     *
     * attritude：一般用于各个顶点各不相同的量。如顶点颜色、坐标等。
     * uniform：一般用于对于3D物体中所有顶点都相同的量。比如光源位置，统一变换矩阵等。
     * varying：表示易变量，一般用于顶点着色器传递到片元着色器的量。
     * const：常量。
     * 限定符与java限定符类似，放在变量类型之前，并且只能用于全局变量。在GLSL中，没有默认限定符一说。
     *
     *lowp：低精度。8位。
     * mediump：中精度。10位。
     * highp：高精度。16位。
     *
     *
     */
    private int mProgram;

    private final int COORDS_PER_VERTEX=3;
    private float triangleCoords[]={
            0.5f,0.5f,0.0f,
            -0.5f,-0.5f,0.0f,
            0.5f,-0.5f,0.0f
    };
    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount=triangleCoords.length/COORDS_PER_VERTEX;
    private final int vertexStride=COORDS_PER_VERTEX*4;

    private float[] color={1.0f,1.0f,1.0f,1.0f};



    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0f, 0f, 0f, 1.0f);
        ByteBuffer buffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);//分配内存占用4个字节
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer=buffer.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);
        int veshader = loadShader(GL_VERTEX_SHADER, vertexShader);
        int fragshader=loadShader(GL_FRAGMENT_SHADER,fragmentShaderCode);
        //
        mProgram=glCreateProgram();
        //设置着色器
        glAttachShader(mProgram,veshader);
        glAttachShader(mProgram,fragshader);
        //链接程序
        glLinkProgram(mProgram);
        int[] linkresult=new int[1];
        glGetProgramiv(mProgram,GL_LINK_STATUS,linkresult,0);
        if (linkresult[0]!=GL_TRUE){
            Log.e("ES20_ERROR", "Could not link program: ");
            Log.e("ES20_ERROR", GLES20.glGetProgramInfoLog(mProgram));
            GLES20.glDeleteProgram(mProgram);
            mProgram = 0;
        }






    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
       glViewport(0, 0, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
       glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
       glUseProgram(mProgram);
       mPositionHandle=glGetAttribLocation(mProgram,"vPosition");
       glEnableVertexAttribArray(mPositionHandle);
       glVertexAttribPointer(mPositionHandle,COORDS_PER_VERTEX,
               GLES20.GL_FLOAT,false,vertexStride,vertexBuffer);
        mColorHandle=GLES20.glGetUniformLocation(mProgram,"vColor");
        GLES20.glUniform4fv(mColorHandle,1,color,0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }


    protected int loadShader(int type, String shaderCode){
        int shader= GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader,shaderCode);
        GLES20.glCompileShader(shader);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e("ES20_ERROR", "Could not compile shader " + type + " : "+shaderCode);
            Log.e("ES20_ERROR", GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        }
        return shader;
    }
}
