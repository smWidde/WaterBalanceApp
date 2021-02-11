package com.example.watercounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private HumanParameters getHumanParameters()
    {
        Boolean sex = ((Switch)findViewById(R.id.sexSwitch)).isChecked();
        Integer height = Integer.parseInt(((EditText)findViewById(R.id.heightValue)).getText().toString());
        Integer weight = Integer.parseInt(((EditText)findViewById(R.id.weightValue)).getText().toString());
        if(weight>200||weight<30)
        {
            Toast.makeText(this, "Допустимый диапозон веса 30...200", Toast.LENGTH_LONG).show();
            return null;
        }
        if(height>300||height<50)
        {
            Toast.makeText(this, "Допустимый диапозон роста 50...300", Toast.LENGTH_LONG).show();
            return null;
        }
        return new HumanParameters(sex, height, weight);
    }
    public void goNext(View view)
    {
        String path = "text.txt";
        try {

            HumanParameters hp = getHumanParameters();
            if(hp==null)
                return;
            SaveHuman(path);
            MyDialogFragment frag = new MyDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("path", path);
            frag.setArguments(bundle);
            frag.show(getSupportFragmentManager(),"custom");
        }
        catch (NumberFormatException ex)
        {
            Toast.makeText(this, "Неправильно введены данные", Toast.LENGTH_LONG).show();
        }
    }
    public void SaveHuman(String path)
    {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(path, MODE_PRIVATE);
            HumanParameters hp = getHumanParameters();
            if(hp==null)
                return;
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