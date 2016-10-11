package com.example.teasunkim.bighead_gles3;

/**
 * Created by TeasunKim on 2016-10-11.
 */
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    private FloatBuffer vertexBuffer;
    private final String vertexShaderCode =
                "#version 300 es                            \n" +
                        "layout (location = 0) in vec4 vPosition;   \n" +
                        "void main() {                              \n" +
                        "   gl_Position = vPosition;                \n" +
                        "}                                          \n";

        private final String fragmentShaderCode =
                "#version 300 es                            \n" +
                        "precision mediump float;                   \n" +
                        "uniform vec4 vColor;                       \n" +
                        "out vec4 fragColor;                        \n" +
                        "void main() {                              \n" +
                        "  fragColor = vColor;                      \n" +
                        "}                                          \n";
    static final int COORDS_PER_VERTEX = 3; // x, y, z
    static float triangleCoords[] = {       // 반시계 방향으로 좌표 설정.
            0.0f,  0.622008459f, 0.0f,      // top
            -0.5f, -0.311004243f, 0.0f,     // bottom left
            0.5f, -0.311004243f, 0.0f       // bottom right
    };

    // 삼각형의 색깔을 설정.
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    private final int mProgram;

/* ... */

    public Triangle() {

        // 4바이트 float * 삼각형 좌표 배열의 수에 해당하는 만큼 버퍼를 할당
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);

        // 디바이스 하드웨어에서 사용하는 byte order를 사용.
        bb.order(ByteOrder.nativeOrder());

        // ByteBuffer를 통해 floating point 버퍼를 생성.
        vertexBuffer = bb.asFloatBuffer();
        // 이 FloatBuffer에 좌표를 추가.
        vertexBuffer.put(triangleCoords);
        // 버퍼 상의 첫 번째 좌표를 읽을 수 있도록 설정.
        vertexBuffer.position(0);

        // 쉐이더 로드 및 컴파일.
        int vertexShader = GLES30Renderer.loadShader(GLES30.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = GLES30Renderer.loadShader(GLES30.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

//        // 프로그램의 생성.
//        mProgram = GLES30.glCreateProgram();
//        GLES30.glAttachShader(mProgram, vertexShader);
//        GLES30.glAttachShader(mProgram, fragmentShader);
//        GLES30.glLinkProgram(mProgram);

        // 프로그램의 생성.
        mProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(mProgram, vertexShader);
        GLES30.glAttachShader(mProgram, fragmentShader);
        GLES30.glLinkProgram(mProgram);

    }

    // draw 함수에 관련 된 변수들.
    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public void draw() {
        // 쉐이더가 포함 된 프로그램을 사용.
        GLES30.glUseProgram(mProgram);

        // 프로그램에 포함 된 쉐이더에서 vPosition 변수에 접근할 수 있는 핸들을 가져온다.
        mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");
        // 해당 값에 대한 Vertex Attribute Array를 활성화한다.
        GLES30.glEnableVertexAttribArray(mPositionHandle);
        // 활성화된 Vertex Attribute Array가 정점 데이터를 가리키도록 한다. (그래픽 장치로 복사)
        GLES30.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, vertexStride, vertexBuffer);
        // vColor 변수에 접근할 수 있는 핸들을 가져온다.
        mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
        // 해당 핸들을 이용해 해당 uniform 변수에 값을 할당한다.
        GLES30.glUniform4fv(mColorHandle, 1, color, 0);

        // 위에서 설정한 값들을 바탕으로 렌더링을 수행한다.
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

        // 활성화된 Vertex Attribute Array를 비활성화한다.
        GLES30.glDisableVertexAttribArray(mPositionHandle);
    }
}
