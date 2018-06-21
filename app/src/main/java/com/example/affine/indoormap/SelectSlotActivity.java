package com.example.affine.indoormap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SelectSlotActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectslot);
        findViewById(R.id.Slot1).setOnClickListener(onClickListener);
        findViewById(R.id.Slot2).setOnClickListener(onClickListener);
        findViewById(R.id.Slot3).setOnClickListener(onClickListener);
        findViewById(R.id.Slot4).setOnClickListener(onClickListener);
        findViewById(R.id.Slot5).setOnClickListener(onClickListener);
        findViewById(R.id.Slot6).setOnClickListener(onClickListener);
        findViewById(R.id.Slot7).setOnClickListener(onClickListener);
        findViewById(R.id.Slot8).setOnClickListener(onClickListener);
        findViewById(R.id.Slot9).setOnClickListener(onClickListener);
        findViewById(R.id.Slot10).setOnClickListener(onClickListener);
        findViewById(R.id.Slot11).setOnClickListener(onClickListener);
        findViewById(R.id.Slot12).setOnClickListener(onClickListener);
        findViewById(R.id.Slot13).setOnClickListener(onClickListener);
        findViewById(R.id.Slot14).setOnClickListener(onClickListener);
        findViewById(R.id.Slot15).setOnClickListener(onClickListener);
        findViewById(R.id.Slot16).setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(SelectSlotActivity.this, MainActivity.class);
            myIntent.putExtra("Slot", getId(v));
            SelectSlotActivity.this.startActivity(myIntent);
        }
    };

    private String getId(View view) {
        TextView textView = (TextView) view;
        return textView.getText().toString();
    }
}
