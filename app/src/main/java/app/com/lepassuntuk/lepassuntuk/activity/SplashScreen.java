package app.com.lepassuntuk.lepassuntuk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import app.com.lepassuntuk.lepassuntuk.R;

import app.com.lepassuntuk.lepassuntuk.MainActivity;

public class SplashScreen extends AppCompatActivity {

//    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        videoView = (VideoView) findViewById(R.id.logo);

//        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);
//        videoView.setVideoURI(video);
//
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            public void onCompletion(MediaPlayer mp) {
//                startNextActivity();
//            }
//        });
//
//        videoView.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);

                SplashScreen.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        }, 2000);
    }
//    private void startNextActivity() {
//        if (isFinishing())
//            return;
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }
}
