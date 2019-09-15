package com.example.android.workout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TimerDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog, null);

        NumberPicker numberPickerMin = view.findViewById(R.id.number_picker_min);
        NumberPicker numberPickerSec = view.findViewById(R.id.number_picker_sec);
        numberPickerMin.setMaxValue(59);
        numberPickerSec.setMaxValue(11);

        // Change sec values to increment in 5
        NumberPicker.Formatter formatter1 = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                int temp = value * 5;
                if (temp >= 10) {
                    return "" + temp;
                } else {
                    return "0" + temp;
                }
            }
        };
        numberPickerSec.setFormatter(formatter1);

        // Change min values to have 0 in front
        NumberPicker.Formatter formatter2 = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                if (value >= 10) {
                    return "" + value;
                } else {
                    return "0" + value;
                }
            }
        };
        numberPickerMin.setFormatter(formatter2);



        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
