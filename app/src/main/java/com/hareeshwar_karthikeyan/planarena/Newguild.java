package com.hareeshwar_karthikeyan.planarena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Newguild extends AppCompatActivity {
    String gng,gdg,gpg;
    EditText gn,gd,gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newguild);
        gn = (EditText) findViewById(R.id.guildnames);
        gd = (EditText) findViewById(R.id.guilddescriptions);
        gp = (EditText) findViewById(R.id.guildpasswords);

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
    }

    public void checkforvalidity(View view)
    {
        gng = gn.getText().toString();
        gpg = gp.getText().toString();
        gdg = gd.getText().toString();
        boolean a = containsNonWhitespaceChar(gng);
        boolean b = containsNonWhitespaceChar(gpg);
        boolean c = containsNonWhitespaceChar(gdg);
        if(a && b && c)
        {
           createguild();
           finish();
           overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
        }
        else
            Toast.makeText(Newguild.this,"Please Enter Valid Inputs",Toast.LENGTH_SHORT).show();

    }

    //checking if non null
    private static boolean containsNonWhitespaceChar(String arg)
    {
        return !((arg == null) || "".equals(arg.trim()));
    }

    private void createguild()
    {
        String pushid = GuildsList.guildsRef.push().getKey();
        GuildsList.usersRef.child(GuildsList.userid).child("Guilds").child(pushid).setValue(true);
        GuildsList.guildsRef.child(pushid).child("description").setValue(gdg);
        GuildsList.guildsRef.child(pushid).child("name").setValue(gng);
        GuildsList.guildsRef.child(pushid).child("password").setValue(gpg);
    }
}
