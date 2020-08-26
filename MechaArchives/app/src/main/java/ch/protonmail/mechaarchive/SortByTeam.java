package ch.protonmail.mechaarchive;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.view.View;

public class SortByTeam extends AppCompatActivity {

    //Objects
    Button _exitButton;
    Button _search;
    EditText _teamNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_by_team);

        _exitButton = (Button) findViewById(R.id.dExitButton);
        _search = (Button) findViewById(R.id.dSearch);
        _teamNum = (EditText) findViewById(R.id.dTeamNum);

        CheckForLiveEvents();
    }

    public void CheckForLiveEvents() {
        _exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }

        });

        if(_teamNum.getText().toString() != null) {

            _search.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), ViewTeamStats.class);
                    myIntent.putExtra("SelectedTeamNum", _teamNum.getText().toString());
                    //startActivityForResult(myIntent, 0);
                    startActivity(myIntent);
                }
            });

        }else{
            giveToastMessage("Please Enter A Team Number");
        }
    }

    public void giveToastMessage(String msg){

        Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
        toast.show();

    }
}
