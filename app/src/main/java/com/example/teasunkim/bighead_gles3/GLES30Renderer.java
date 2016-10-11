package com.example.teasunkim.bighead_gles3;

/**
 * Created by TeasunKim on 2016-10-11.
 */
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLES30Renderer implements GLSurfaceView.Renderer{
    Triangle mTriangle;

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        mTriangle.draw();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // viewport를 설정한다.
        GLES30.glViewport(0, 0, width, height);
    }

    public static int loadShader(int type, String shaderCode) {
        // 빈 쉐이더를 생성하고 그 인덱스를 할당.
        int shader = GLES30.glCreateShader(type);

        // *컴파일 결과를 받을 공간을 생성.
        IntBuffer compiled = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        String shaderType;

        // *컴파일 결과를 출력하기 위해 쉐이더를 구분.
        if(type == GLES30.GL_VERTEX_SHADER)
            shaderType = "Vertex";
        else if(type == GLES30.GL_FRAGMENT_SHADER)
            shaderType = "Fragment";
        else
            shaderType = "Unknown";

        // 빈 쉐이더에 소스코드를 할당.
        GLES30.glShaderSource(shader, shaderCode);
        // 쉐이더에 저장 된 소스코드를 컴파일
        GLES30.glCompileShader(shader);

        // *컴파일 결과 오류가 발생했는지를 확인.
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled);
        // *컴파일 에러가 발생했을 경우 이를 출력.
        if(compiled.get(0) == 0) {
            GLES30.glGetShaderiv(shader,GLES30.GL_INFO_LOG_LENGTH,compiled);
            if (compiled.get(0) > 1){
                Log.e("Shader", shaderType + " shader: " + GLES30.glGetShaderInfoLog(shader));
            }
            GLES30.glDeleteShader(shader);
            Log.e("Shader", shaderType + " shader compile error.");
        }

        // 완성된 쉐이더의 인덱스를 리턴.
        return shader;
    }
}

/*GLES30Renderer 클래스는 GLSurfaceView에서 실제로 렌더링을 수행하는 Renderer 클래스를 상속 받으며 기본적으로 위와 같은 메서드들을 오버라이딩 함으로서 스스로의 역할을 수행합니다. 각 메서드들의 역할은 다음과 같습니다.

onSurfaceCreated – GLSurfaceView가 생성 될 때 최초 한 번만 실행이 되는 메서드
onDrawFrame – 반복해서 호출되는 메서드. glut에서 Display 함수 역할과 동일하다.
onSurfaceChanged – GLSurfaceView에 변화가 있을 때 호출되는 메서드. 화면이 회전되거나 앱을 나갔다 다시 들어오는 등의 순간에 호출된다. */