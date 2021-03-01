package com.example.watercounter;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OtherActivity extends AppCompatActivity {
    private HumanParameters hp;
    private TextView ToValue;
    private TextView DoneValue;
    private String path;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_counter);
        Bundle extra = getIntent().getExtras();
        if(extra==null)
        {
            register();
        }
        path = extra.getString("path");
        if(path==null)
        {
            register();
        }
        FileInputStream fin = null;
        try {
            fin = this.openFileInput(path);
        } catch (FileNotFoundException e) {
            if(fin!=null) {
                try {
                    fin.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        hp = null;
        try {
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            hp = new HumanParameters(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(hp==null)
        {
            register();
        }
        ToValue = findViewById(R.id.textViewToValue);
        DoneValue = findViewById(R.id.textViewDoneValue);
        ToValue.setText(((hp.MillilitresToDrink/250)+"x"));
        DoneValue.setText(((hp.MillilitresDrank/250)+"x"));
    }
    private void register()
    {
        Intent intent =  new Intent(this, OtherActivity.class);
        this.startActivity(intent);
    }
    public void AddCup(View view)
    {
        view.setVisibility(View.GONE);
        ImageView img = findViewById(R.id.imageView3);
        img.setImageBitmap(null);
        img.setBackground(null);
        img.setBackgroundResource(R.drawable.cup_animation);
        final AnimationDrawable myAnim = (AnimationDrawable) img.getBackground();
        myAnim.start();
        view.postDelayed(()->{
            view.setVisibility(View.VISIBLE);
            hp.MillilitresDrank += 250;
            DoneValue.setText(((hp.MillilitresDrank/250)+"x"));
            SaveHuman();
            img.setBackground(null);
            img.setBackgroundResource(R.drawable.cup);
        }, 1500);
    }
    public void SaveHuman()
    {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(path, MODE_PRIVATE);
            if(hp==null)
                register();
            fos.write(hp.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos!=null)
            {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
