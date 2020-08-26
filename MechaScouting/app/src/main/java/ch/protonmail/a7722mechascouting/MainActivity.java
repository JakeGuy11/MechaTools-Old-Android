package ch.protonmail.a7722mechascouting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.*;
import android.content.Intent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    Button _newMatchButton;
    Button _newSpecsButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _newMatchButton = (Button) findViewById(R.id.dnewMatchButton);
        _newSpecsButton = (Button) findViewById(R.id.dnewSpecsButton);

        actOnElements();

    }

    public void actOnElements(){

        _newMatchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), InputMatch.class);
                startActivityForResult(myIntent, 0);
            }});

        _newSpecsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), InputSpecs.class);
                startActivityForResult(myIntent, 0);
            }});

    }
}