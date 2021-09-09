package com.majdj_jameels.ex1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button playbtn;
    private Switch musicbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playbtn = findViewById(R.id.play);
        playbtn.setOnClickListener(this);
        musicbtn = findViewById(R.id.music);
        musicbtn.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        boolean musicOn = sp.getBoolean("user", false);
//        check if the switch was on or off last time the user used the app and turn it on/off accordingly
        musicbtn.setChecked(musicOn);
        musicbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();
                if (isChecked)
                    editor.putBoolean("user", true);
                else
                    editor.putBoolean("user", false);

                editor.commit();

            }
        });



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                startActivity(new Intent(MainActivity.this, GameActivity.class));
                break;

        }
    }
//    Dialog configuration
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        MenuItem aboutMenu = menu.add("About");
        MenuItem exitMenu = menu.add("Exit");

        aboutMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                showAboutDialog();
                return true;
            }
        });

        exitMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                showExitDialog();
                return true;
            }
        });




        return true;
    }

    public void showAboutDialog() {

        String aboutApp = getString(R.string.app_name) +" "+
                "(" + getPackageName() +")"+"\n\n" +
                "By Jameel Silwadi & Majd Jaber, 23/3/21.";

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("About App");
        dialog.setMessage(aboutApp);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showExitDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_exit);
        dialog.setTitle("Exit App");
        dialog.setMessage("Do you really want to exit Puzzle 15 ?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish(); // close this activity
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        dialog.show();
    }



}