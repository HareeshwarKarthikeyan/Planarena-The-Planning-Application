package com.hareeshwar_karthikeyan.planarena;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuildsList extends AppCompatActivity {
    private static FirebaseAuth.AuthStateListener mAuthListener;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static DatabaseReference usersRef,guildsRef;
    public static String username,dpurl,userid;
    private FirebaseAuth mAuth;

    public static ArrayList<Guilds> guildsList;
    public static Guilds selectedguild;
    GridView gridView;
    public static GridViewAdapter guildsadapter;
    public static int guildpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guilds_list);

        gridView = (GridView) findViewById(R.id.guildlistsgrid);

        // gridview
        guildsList = new ArrayList<>();
       guildsadapter = new GridViewAdapter(GuildsList.this,guildsList);

        //closing on user not logged in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent f = new Intent(GuildsList.this, StartActivity.class);
                    startActivity(f);
                    finish();
                }
            }
        };

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        usersRef = mDatabaseReference.child("users");
        guildsRef = mDatabaseReference.child("Guilds");

        //getting user name details for post info
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(GuildsList.this);
        if (acct != null) {
            //getting user profile data
            username = acct.getDisplayName();
            Uri personPhoto = acct.getPhotoUrl();
            dpurl = personPhoto.toString();
        }
        //code to write to firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userid = user.getUid();

        //for first time launch
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            //first time task - 1
            // first time task-2
            //Getting firebase user id
            usersRef.child(userid).child("name").setValue(username);
            usersRef.child(userid).child("profileURL").setValue(dpurl);
            usersRef.child(userid).child("provider").setValue("google.com");
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();

        }

        //Customising the Action Bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.guildslist_abs_layout);
        getSupportActionBar().setElevation(0.25f);

        getdata();

        //onclick listener for each grid item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedguild = guildsList.get(position);
                guildpos = position;
                Intent tocreatepost = new Intent(GuildsList.this, EventsList.class);
                startActivity(tocreatepost);
                overridePendingTransition(R.anim.slide_left_info, R.anim.no_change);
            }
        });

    }


    public void getdata()
    {
        usersRef.child(GuildsList.userid).child("Guilds").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getValue(Boolean.class) == true )
                {
                   final Guilds guild = new Guilds();
                    guild.setId(dataSnapshot.getKey());


                    guildsRef.child(guild.getId()).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()!=null){
                                guild.setName(dataSnapshot.getValue(String.class));
                                guildsadapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    guildsRef.child(guild.getId()).child("description").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()!=null){
                                guild.setDescription(dataSnapshot.getValue(String.class));
                                guildsadapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    guildsRef.child(guild.getId()).child("Events").addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                            if(dataSnapshot.getValue()!=null){
//                                guild.addEventid(dataSnapshot.getKey());
//                                guildsadapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });

                    guildsList.add(guild);
                    gridView.setAdapter(guildsadapter);
                }
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
        ArrayList<Guilds> guilds;

        public GridViewAdapter(Context context, ArrayList<Guilds> guilds)
        {
            this.context = context;
            this.guilds = guilds;
        }

        @Override
        public int getCount()
        {
            return guilds.size();
        }

        @Override
        public Object getItem(int position)
        {
            return guilds.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(GuildsList.this).inflate(R.layout.guildlistitem,parent,false);


            //views
            TextView des,title,ue;
            des = (TextView) convertView.findViewById(R.id.guilddescription);
             title = (TextView) convertView.findViewById(R.id.guildtitle);
             ue = (TextView) convertView.findViewById(R.id.upcomingevent);


            //assigning data

            title.setText(guilds.get(position).getName());
            des.setText(guilds.get(position).getDescription());

            return convertView;
        }

    }




    public void signout(View view)
    {
        final View view1 = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu logoutpopupmenu = new PopupMenu(GuildsList.this, view1);
                logoutpopupmenu.getMenuInflater().inflate(R.menu.logoutpopup, logoutpopupmenu.getMenu());

                logoutpopupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals("Log Out")) {
                    //logging out
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent f = new Intent(GuildsList.this, StartActivity.class);
                    startActivity(f);
                }
                else if(item.getTitle().equals("Join Guild")){
                    Intent f = new Intent(GuildsList.this, Joinguild.class);
                    startActivity(f);
                    overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
                }
                return true;
                    }
                });
                logoutpopupmenu.show();
            }
        });
        view.performClick();
    }

    public void newguild(View view)
    {
        Intent f = new Intent(GuildsList.this, Newguild.class);
        startActivity(f);
        overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);

    }

}
