package abdelfatah.mostafa.com.rosary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import java.sql.Time;

public class BeginingActivity extends AppCompatActivity {

    private int time=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        setContentView(R.layout.activity_begining);
        /* new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent page = new Intent();
                page.setClass(getApplicationContext(), MainActivity.class);
                startActivity(page);
                finish();
            }
        }, 5000);*/
        /*
        final Handler handler = new Handler();
           handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent page = new Intent();
                page.setClass(getApplicationContext(), MainActivity.class);
                startActivity(page);
                finish();
            }
        }, 5000);
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) { }
                Intent page = new Intent();
                page.setClass(getApplicationContext(), MainActivity.class);
                startActivity(page);
                finish();
            }
        }).start();

    }
}
