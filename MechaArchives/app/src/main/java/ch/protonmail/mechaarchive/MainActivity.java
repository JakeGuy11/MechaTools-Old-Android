package ch.protonmail.mechaarchive;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    Button _teamButton;
    Button _tagButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _teamButton = (Button) findViewById(R.id.dTeamSort);
        _tagButton = (Button) findViewById(R.id.dTagSort);

        CheckForLiveEvents();

    }

    public void CheckForLiveEvents(){

        _teamButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SortByTeam.class);
                startActivityForResult(myIntent, 0);
            }});

        _tagButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                giveToastMessage("Coming Soon!");
                //Intent myIntent = new Intent(view.getContext(), SortByTag.class);
                //startActivityForResult(myIntent, 0);
            }});

    }

    public void giveToastMessage(String msg){

        Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
        toast.show();

    }
}