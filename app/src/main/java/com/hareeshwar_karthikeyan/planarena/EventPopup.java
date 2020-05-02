package com.hareeshwar_karthikeyan.planarena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventPopup extends AppCompatActivity {
        TextView title,location,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_popup);
        title = (TextView) findViewById(R.id.evnam);
        location = (TextView) findViewById(R.id.locn);
        time = (TextView) findViewById(R.id.tim);
        title.setText(EventsList.newevent.getName());
        location.setText(EventsList.newevent.getLocation());
        time.setText(EventsList.newevent.getTime());
    }

    public void oneone(View view){
        GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                .child(EventsList.newevent.getId()).child("attendees").child(GuildsList.userid).setValue(1);
        finish();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
    }
    public void twotwo (View view){
        GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                .child(EventsList.newevent.getId()).child("attendees").child(GuildsList.userid).setValue(2);
        finish();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
    }
    public void threethree (View view){
        GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                .child(EventsList.newevent.getId()).child("attendees").child(GuildsList.userid).setValue(3);
        finish();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
    }
}
