package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ImageView1 extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView image;
    private static int splash=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_view);

        image=findViewById(R.id.imageView);

        Handler h=new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(ImageView1.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },splash);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this, R.anim.top_bottom);


        image.startAnimation(topAnim);


    }
}
