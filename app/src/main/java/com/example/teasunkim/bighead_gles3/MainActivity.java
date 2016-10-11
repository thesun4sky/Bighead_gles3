package com.example.teasunkim.bighead_gles3;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    // 렌더링 결과를 그릴 OpenGL view를 선언.
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // GLES30SurfaceView 라는 클래스를 이용해서 GLSurfaceView를 초기화 할 예정.
        mGLView = new GLES30SurfaceView(this);
        setContentView(mGLView);    // mGLView 객체로 contentView를 설정해준다.
    }
}

class GLES30SurfaceView extends GLSurfaceView {
    private final GLES30Renderer mRenderer;

    public GLES30SurfaceView(Context context){
        super(context);

        // OpenGL ES 3.0 context를 생성.
        setEGLContextClientVersion(3);
        mRenderer = new GLES30Renderer();

        // GLSurfaceView에 렌더링 결과를 그리기 위해 현재 SurfaceView에 렌더러를 설정해준다.
        setRenderer(mRenderer);
    }
}