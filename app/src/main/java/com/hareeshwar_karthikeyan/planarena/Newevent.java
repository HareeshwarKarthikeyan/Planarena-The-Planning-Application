package com.hareeshwar_karthikeyan.planarena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.util.Date;

public class Newevent extends AppCompatActivity {
String dat,den,el,dt;
EditText name,desc,loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newevent);

        name = (EditText)findViewById(R.id.tile);
        desc = (EditText) findViewById(R.id.desc);
        loc = (EditText) findViewById(R.id.loc);

        //obtaining date and time
        final SingleDateAndTimePicker singleDateAndTimePicker = (SingleDateAndTimePicker) findViewById(R.id.single_day_picker);
        singleDateAndTimePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
                dt = date.toString();
                desc.setText(dt);

            }
        });

    }

    public void check(View view)
    {
        dat = name.getText().toString();
        el = loc.getText().toString();
        den = desc.getText().toString();
        boolean a = containsNonWhitespaceChar(dat);
        boolean b = containsNonWhitespaceChar(el);
        boolean c = containsNonWhitespaceChar(den);
        if(a && b && c)
        {
            createevent();
            finish();
            overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
        }
        else
            Toast.makeText(Newevent.this,"Please Enter Valid Inputs",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
    }

    //checking if non null
    private static boolean containsNonWhitespaceChar(String arg)
    {
        return !((arg == null) || "".equals(arg.trim()));
    }

    private void createevent()
    {
        String pushid = GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events").push().getKey();
        GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                .child(pushid).child("description").setValue(den);
        GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                .child(pushid).child("location").setValue(el);
        GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
            .child(pushid).child("name").setValue(dat);
        GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                .child(pushid).child("time").setValue(dt);
    }

    public void finish(View view)
    {
        finish();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
    }
}
