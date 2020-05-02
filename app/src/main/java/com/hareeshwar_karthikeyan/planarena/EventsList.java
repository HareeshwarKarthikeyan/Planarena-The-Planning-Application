package com.hareeshwar_karthikeyan.planarena;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsList extends AppCompatActivity {
        TextView title;

    public static ArrayList<Events> eventsList;
    public static Events selectedevent;
    GridView gridView;
    public static GridViewAdapter eventsadapter;
    public static int eventpos;
    public static Events newevent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        //Customising the Action Bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.eventslist_abs_layout);
        getSupportActionBar().setElevation(0.25f);
        title = (TextView) findViewById(R.id.guildo);
        title.setText(GuildsList.selectedguild.getName());

        gridView = (GridView) findViewById(R.id.eventslistsgrid);

        // gridview
        eventsList = new ArrayList<>();
        eventsadapter = new GridViewAdapter(EventsList.this,eventsList);

        getevents();



        //onclick listener for each grid item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedevent = eventsList.get(position);
                eventpos = position;
                Intent tocreatepost = new Intent(EventsList.this, Eacheventactivity.class);
                startActivity(tocreatepost);
                overridePendingTransition(R.anim.slide_left_info, R.anim.no_change);
            }
        });


    }

    public void getevents()
    {
        GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    final Events event = new Events();
                    event.setId(dataSnapshot.getKey());

                GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                        .child(event.getId()).child("description").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()!=null){
                            event.setDescription(dataSnapshot.getValue(String.class));
                            eventsadapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                        .child(event.getId()).child("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()!=null){
                            event.setName(dataSnapshot.getValue(String.class));
                            eventsadapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                        .child(event.getId()).child("time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()!=null){
                            event.setTime(dataSnapshot.getValue(String.class));
                            eventsadapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                        .child(event.getId()).child("location").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()!=null){
                            event.setLocation(dataSnapshot.getValue(String.class));
                            eventsadapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                GuildsList.guildsRef.child(GuildsList.selectedguild.getId()).child("Events")
                        .child(event.getId()).child("attendees").child(GuildsList.userid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists() || dataSnapshot.getValue(Long.class)==3){
                            newevent = event;
                            Intent intent = new Intent(EventsList.this, EventPopup.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                eventsList.add(event);
                gridView.setAdapter(eventsadapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public class GridViewAdapter extends BaseAdapter {

        Context context;
        ArrayList<Events> events;

        public GridViewAdapter(Context context, ArrayList<Events> events)
        {
            this.context = context;
            this.events = events;
        }

        @Override
        public int getCount()
        {
            return events.size();
        }

        @Override
        public Object getItem(int position)
        {
            return events.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            TextView loc,title,time;

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(EventsList.this).inflate(R.layout.eventslistitem,parent,false);


            //views
            time = (TextView) convertView.findViewById(R.id.eventtime);
            title = (TextView) convertView.findViewById(R.id.guildtitle);
            loc = (TextView) convertView.findViewById(R.id.eventlocation);


            //assigning data
//
//            title.setText(events.get(position).getName());
//            loc.setText(events.get(position).getLocation());
//            time.setText(events.get(position).getTime());

            return convertView;
        }

    }


    public void newevent(View view)
    {
        Intent f = new Intent(EventsList.this, Newevent.class);
        startActivity(f);
        overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);

    }

    public void goback(View view)
    {
        finish();
        overridePendingTransition(R.anim.no_change,R.anim.slide_right_info);
    }

    public void share(View view)
    {
        String sharedata = "Hello! Welcome to my PlanArena Guild!\nGuildname : XXXXXX "+'\n'+"Password : XXXXXX";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent .setType("text/plain");
        intent .putExtra(Intent.EXTRA_TEXT, sharedata);
        startActivity(Intent.createChooser(intent, "Share image via"));
    }
}
