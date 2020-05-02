package com.hareeshwar_karthikeyan.planarena;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class Joinguild extends AppCompatActivity {

    String name,password,id;
    EditText n,p;
    String pass;
    ArrayList<String> names,pws,ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinguild);
        n = (EditText) findViewById(R.id.joinn);
        p = (EditText) findViewById(R.id.joinpw);

        names = new ArrayList<String>();
        pws = new ArrayList<String>();
        ids = new ArrayList<String >();

        GuildsList.guildsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey()!=null) {
                    names.add(dataSnapshot.child("name").getValue(String.class));
                    pws.add(dataSnapshot.child("password").getValue(String.class));
                    ids.add(dataSnapshot.getKey());
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

    public void join(View view)
    {
     name = n.getText().toString();
     password = p.getText().toString();

        for(int i =0;i<names.size();i++)
        {
            if(names.get(i).equals(name)){
                if(pws.get(i).equals(password)) {
                    GuildsList.usersRef.child(GuildsList.userid).child("Guilds").child(ids.get(i)).setValue(true);
                    GuildsList.guildsRef.child(ids.get(i)).child("members").child(GuildsList.userid).setValue(true);
                    Toast.makeText(Joinguild.this, "Joined new guild successfuly !", Toast.LENGTH_SHORT).show();
                    finish();
                    i=names.size();
                }
                else{
                    Toast.makeText(Joinguild.this, "Invalid Guild Name or Password", Toast.LENGTH_SHORT).show();
                    i = names.size();
                }
            break;
            }
        }




    }
}
