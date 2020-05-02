package com.hareeshwar_karthikeyan.planarena;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Eacheventactivity extends AppCompatActivity {

    TextView title;
    TextView desc,swipe;
    ImageView swipeup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eacheventactivity);

        //Customising the Action Bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.eachevent_abs_layout);
        getSupportActionBar().setElevation(0.25f);
        title = (TextView) findViewById(R.id.evento);
        swipe = (TextView) findViewById(R.id.swipe);
        swipeup = (ImageView) findViewById(R.id.swipeup);
        desc = (TextView) findViewById(R.id.eventdescription);
        title.setText(EventsList.selectedevent.getName());
        String d = EventsList.selectedevent.getDescription()+'\n'+EventsList.selectedevent.getLocation()
                +'\n'+EventsList.selectedevent.getTime();
        desc.setText(d);

        final GestureDetector gesture = new GestureDetector(Eacheventactivity.this,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {


                        try {
                            if (e1.getY() > e2.getY()) {
                                Intent f = new Intent(Eacheventactivity.this,Chat.class);
                                startActivity(f);
                                overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);                            }

                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });
        swipe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        swipeup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_change,R.anim.slide_right_info);
    }

    public void newtask(View view)
    {
        Intent f = new Intent(Eacheventactivity.this,Newtask.class);
        startActivity(f);
        overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);

    }
    public void fin(View view)
    {
        finish();
        overridePendingTransition(R.anim.no_change,R.anim.slide_right_info);
    }
}
