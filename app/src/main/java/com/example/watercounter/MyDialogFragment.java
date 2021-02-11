package com.example.watercounter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FileInputStream fin = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.human_info_layout, null);
        try {
            fin = getActivity().openFileInput(getArguments().getString("path"));
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            HumanParameters hp = new HumanParameters(bytes);
            ((TextView)view.findViewById(R.id.heightValue2)).setText(hp.Height.toString());
            ((TextView)view.findViewById(R.id.sexValue2)).setText((hp.Sex?"Женский":"Мужской"));
            ((TextView)view.findViewById(R.id.weightValue2)).setText(hp.Weight.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder
                .setView(view)
                .setTitle("Вы согласны?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =  new Intent(getActivity(), OtherActvity.class);
                        intent.putExtra("path", getArguments().getString("path"));
                        getActivity().startActivity(intent);
                    }
                })
                .setNegativeButton("Нет", null)
                .create();
    }
}