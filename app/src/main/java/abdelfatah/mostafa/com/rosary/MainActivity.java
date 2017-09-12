package abdelfatah.mostafa.com.rosary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final CharSequence[] ALAzkaritems={ "لا اله الا الله","سبحان الله","الحمدلله","الله اكبر","استغفر الله"
            ,"سبحان الله وبحمده سبحان الله العظيم","صلي علي محمد"  ,"لا اله الا الله سبحانك" +
            " اني كنت من الظالمين "
            ,"لا حول ولا قوة الا بالله","حسبي الله ونعم الوكيل"};

    final CharSequence[] Settingitems={"تفعيل الصوت","تفعيل الاهتزاز"};
    String[]CountNumber={"1","2","3","4","5","6","7","8","9","10"};
    final boolean[]Checked={false,false};
    int selelcted=0;

    AlertDialog.Builder builder1 = null;
    AlertDialog.Builder builder2 = null;
    AlertDialog dialog = null;
    TextView text;
    TextView countText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        countText = (TextView) findViewById(R.id.counter);
        text=(TextView)findViewById(R.id.Azkartext);
        setData();
        //************************* ALAzkar action **************************************
        builder1 = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        builder1.setTitle("اختار الذكر")
                .setSingleChoiceItems(ALAzkaritems, -1 , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CountNumber[selelcted] = countText.getText().toString();
                        selelcted = which;
                        saveData();
                        text.setText(ALAzkaritems[which]);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        //************************* Setting action **************************************
        builder2 = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        builder2.setTitle("الاعدادات")
                .setMultiChoiceItems(Settingitems, Checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Checked[which] = isChecked;
                        saveData();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "تطبيق لا تنسي ذكر الله");
            intent.putExtra(Intent.EXTRA_TEXT, "تطبيق السبحة يساعدك في ذكر الله"+
                    "\nhttps://play.google.com/store/apps/details?id=abdelfatah.mostafa.com.rosary");
            startActivity(Intent.createChooser(intent, "Please Choose One ......."));
            return true;
        }else if (id == R.id.action_Valuable) {
            final String appPackageName = getPackageName();  // getPackageName() طلبنا اسم الباكيج الخاص للتطبيق من هذا التطبيق, لو أردت تقييم تطبيق اخر ضع اسم الباكيج الخاصة به
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            return true;
        }else if (id == R.id.action_more) {
            String developerName = "MostafaAbdEl_Fatah";  // your name in google play
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q="+developerName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q="+developerName)));
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void Count_Clicked(View view) {
        if (Checked[0]) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                }

                ;
            });
        }
        if (Checked[1]) {
            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(100);
        }

        TextView countText = (TextView) findViewById(R.id.counter);
        int num = Integer.parseInt(String.valueOf(countText.getText()));
        CountNumber[selelcted]= String.valueOf(++num);
        countText.setText(String.valueOf(num));
        saveData();
    }

    public void Reset_counter(View view) {
        if (Checked[0]) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                }

                ;
            });
        }
        TextView countText = (TextView) findViewById(R.id.counter);
        countText.setText("0");
    }

    public void Menu_Clicked(View view) {
        if (Checked[0]) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                }

                ;
            });
        }
        dialog = builder1.create();
        dialog.show();

    }

    public void Setting_Clicked(View view) {
        if (Checked[0]) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                }

                ;
            });
        }
        dialog = builder2.create();
        dialog.show();
    }

    public void saveData(){
        String count=CountNumber[0];
        for (int i=1 ; i<CountNumber.length ; i++ ) {
            count+=","+CountNumber[i];
        }
        SharedPreferences sharedPreferences=getSharedPreferences("Setting", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("selelcted",selelcted);
        editor.putBoolean("checkedsound",Checked[0]);
        editor.putBoolean("checkedvibration",Checked[1]);
        editor.putString("Counts",count);
        editor.commit();
        setData();
    }

    public void setData(){
        SharedPreferences sharedPreferences=this.getSharedPreferences("Setting", MainActivity.this.MODE_PRIVATE);
        selelcted=sharedPreferences.getInt("selelcted", 0);
        Checked[0] = sharedPreferences.getBoolean("checkedsound",true);
        Checked[1]=sharedPreferences.getBoolean("checkedvibration", true);
        String count=sharedPreferences.getString("Counts", "0,0,0,0,0,0,0,0,0,0");
        CountNumber = count.split(",");
        countText.setText(CountNumber[selelcted]);
        text.setText(ALAzkaritems[selelcted]);
    }




}