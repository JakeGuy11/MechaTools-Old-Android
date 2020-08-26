package ch.protonmail.mechaarchive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewTeamStats extends AppCompatActivity {

    //Spec Objects
    Button _exitButton;
    TextView _title;
    TextView _height;
    TextView _weight;
    TextView _language;
    TextView _experience;
    TextView _hasAuto;
    TextView _shootsAuto;
    TextView _picksAuto;
    TextView _bottomCells;
    TextView _hexCells;
    TextView _bullseyeCells;
    TextView _spinWheel;
    TextView _colorWheel;
    TextView _underWheel;
    TextView _climb1;
    TextView _climb2;
    TextView _climb3;
    TextView _canBalance;
    TextView _canLift;
    TextView _notes;

    //Match Objects
    TextView _percentAuto;
    TextView _autoBottom;
    TextView _autoHex;
    TextView _autoBullseye;
    TextView _teleBottom;
    TextView _teleHex;
    TextView _teleBullseye;
    TextView _percentSpin;
    TextView _percentColor;
    TextView _percentNoEnd;
    TextView _percentClimb;
    TextView _percentBalance;
    TextView _percentLift;
    TextView _penalties;
    TextView _comments;


    //Variables
    String jsonURL; // = "http://7722.ca/get_stats.php?team=LT5";
    JSONObject jsonObj; // = new getJSON().execute(jsonURL).toString();
    //Language: 1=CPP 2=Java 3=Python 4=Other
    //Experience FirstYear=1 SecondYear=2 ThirdYear=3 Fourth+Year=4
    String teamNum;

    //Error Variables
    int JsonError = 1;
    int SpecsUpdateError = 2;
    int MatchUpdateError = 3;

    boolean jsonLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_team_stats);
        Intent i = getIntent();
        teamNum = i.getStringExtra("SelectedTeamNum");

        if(jsonLoaded == false){
            try {
                jsonURL = "http://7722.ca/get_stats.php?team=LT" + teamNum.toString();
                Log.i( "json", "Here - teamNum = " + teamNum.toString());

                int c = 0;
                getJSON myJSON = new getJSON();
                //jsonObj = myJSON.execute(jsonURL).get();
                String test = myJSON.execute(jsonURL).get();
                myJSON.onPostExecute(test);
                jsonObj = new JSONObject(test);
                //giveErrorText(test);
                jsonLoaded = true;
            } catch (Exception e){
                giveErrorCode(JsonError);
                jsonLoaded = true;
            }
        }

        _exitButton = findViewById(R.id.dExitButton);
        _title = findViewById(R.id.dtitleText);
        _height = findViewById(R.id.dHeightLabel);
        _weight = findViewById(R.id.dWeightLabel);
        _language = findViewById(R.id.dCodeLabel);
        _experience = findViewById(R.id.dDriveLabel);
        _hasAuto = findViewById(R.id.dhasAuto);
        _shootsAuto = findViewById(R.id.dshootsAuto);
        _picksAuto = findViewById(R.id.dPicksAuto);
        _bottomCells = findViewById(R.id.dBottomCells);
        _hexCells = findViewById(R.id.dHexCells);
        _bullseyeCells = findViewById(R.id.dBullseyeCells);
        _spinWheel = findViewById(R.id.dSpinWheel);
        _colorWheel = findViewById(R.id.dColorWheel);
        _underWheel = findViewById(R.id.dUnderWheel);
        _climb1 = findViewById(R.id.dClimb1);
        _climb2 = findViewById(R.id.dClimb2);
        _climb3 = findViewById(R.id.dClimb3);
        _canBalance = findViewById(R.id.dCanBalance);
        _canLift = findViewById(R.id.dCanLift);
        _notes = findViewById(R.id.dNotes);

        _percentAuto = findViewById(R.id.dPercentAuto);
        _autoBottom = findViewById(R.id.dAutoBottom);
        _autoHex = findViewById(R.id.dAutoHex);
        _autoBullseye = findViewById(R.id.dAutoBullseye);
        _teleBottom = findViewById(R.id.dTeleBottom);
        _teleHex = findViewById(R.id.dTeleHex);
        _teleBullseye = findViewById(R.id.dTeleBullseye);
        _percentSpin = findViewById(R.id.dPercentSpin);
        _percentColor = findViewById(R.id.dPercentColor);
        _percentNoEnd = findViewById(R.id.dPercentNoEnd);
        _percentClimb = findViewById(R.id.dPercentClimb);
        _percentBalance = findViewById(R.id.dPercentBalance);
        _percentLift = findViewById(R.id.dPercentLift);
        _penalties = findViewById(R.id.dPenalties);
        _comments = findViewById(R.id.dComments);


        UpdatePeriodic();
    }

    public void UpdatePeriodic(){

        _exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }

        });

        try {

            _title.setText("Team " + teamNum);

            _height.setText("Height: " + parseJSONInt("height") + " in");

            _weight.setText("Weight: " + parseJSONInt("weight") + " lbs");

            _notes.setText("Comments: " + parseJSONString("notes"));

            if (parseJSONInt("codeLanguage").equals(1)) {
                _language.setText("Code: C++");
            } else if (parseJSONInt("codeLanguage").equals(2)) {
                _language.setText("Code: Java");
            } else if (parseJSONInt("codeLanguage").equals(3)) {
                _language.setText("Code: Python");
            } else {
                _language.setText("Code: Other");
            }

            if (parseJSONInt("experience") == 1){
                _experience.setText("Driver Experience: First Year Driving");
            } else if (parseJSONInt("experience") == 2) {
                _experience.setText("Driver Experience: Second Year Driving");
            } else if (parseJSONInt("experience") == 3) {
                _experience.setText("Driver Experience: Third Year Driving");
            } else {
                _experience.setText("Driver Experience: Fourth+ Year Driving");
            }

            if (parseJSONInt("hasAuto").equals(1)) {
                _hasAuto.setTextColor(getResources().getColor(R.color.able));
            } else {
                _hasAuto.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("shootsAuto").equals(1)) {
                _shootsAuto.setTextColor(getResources().getColor(R.color.able));
            } else {
                _shootsAuto.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("picksAuto").equals(1)) {
                _picksAuto.setTextColor(getResources().getColor(R.color.able));
            } else {
                _picksAuto.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("bottomCells").equals(1)) {
                _bottomCells.setTextColor(getResources().getColor(R.color.able));
            } else {
                _bottomCells.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("hexCells").equals(1)) {
                _hexCells.setTextColor(getResources().getColor(R.color.able));
            } else {
                _hexCells.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("bullseyeCells").equals(1)) {
                _bullseyeCells.setTextColor(getResources().getColor(R.color.able));
            } else {
                _bullseyeCells.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("spinWheel").equals(1)) {
                _spinWheel.setTextColor(getResources().getColor(R.color.able));
            } else {
                _spinWheel.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("colorWheel").equals(1)) {
                _colorWheel.setTextColor(getResources().getColor(R.color.able));
            } else {
                _colorWheel.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("underWheel").equals(1)) {
                _underWheel.setTextColor(getResources().getColor(R.color.able));
            } else {
                _underWheel.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("climb1").equals(1)) {
                _climb1.setTextColor(getResources().getColor(R.color.able));
            } else {
                _climb1.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("climb2").equals(1)) {
                _climb2.setTextColor(getResources().getColor(R.color.able));
            } else {
                _climb2.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("climb3").equals(1)) {
                _climb3.setTextColor(getResources().getColor(R.color.able));
            } else {
                _climb3.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("canBalance").equals(1)) {
                _canBalance.setTextColor(getResources().getColor(R.color.able));
            } else {
                _canBalance.setTextColor(getResources().getColor(R.color.unable));
            }

            if (parseJSONInt("canLift").equals(1)) {
                _canLift.setTextColor(getResources().getColor(R.color.able));
            } else {
                _canLift.setTextColor(getResources().getColor(R.color.unable));
            }

        }catch (Exception e){
            giveErrorCode(SpecsUpdateError);
        }

        try{

            _percentAuto.setText(String.format("Has Autonomous %.1f %% Of The Time", parseJSONDouble("percentAuto")));

            _autoBottom.setText(String.format("Gets An Average Of %.1f Level 1 Cells", parseJSONDouble("autoBottom")));

            _autoHex.setText(String.format("Gets An Average Of %.1f Level 2 Cells", parseJSONDouble("autoHex")));

            _autoBullseye.setText(String.format("Gets An Average Of %.1f Level 3 Cells", parseJSONDouble("autoBullseye")));

            _teleBottom.setText(String.format("Gets An Average Of %.1f Level 1 Cells", parseJSONDouble("teleBottom")));

            _teleHex.setText(String.format("Gets An Average Of %.1f Level 2 Cells", parseJSONDouble("teleHex")));

            _teleBullseye.setText(String.format("Gets An Average Of %.1f Level 3 Cells", parseJSONDouble("teleBullseye")));

            _percentSpin.setText(String.format("Spins The Wheel %.1f %% Of The Time", parseJSONDouble("percentSpin")));

            _percentColor.setText(String.format("Colours The Wheel %.1f %% Of The Time", parseJSONDouble("percentColor")));

            _percentNoEnd.setText(String.format("Has No Endgame %.1f %% Of The Time", parseJSONDouble("percentNoEnd")));

            _percentClimb.setText(String.format("Climbs %.1f %% Of The Time", parseJSONDouble("percentClimb")));

            _percentBalance.setText(String.format("Finishes Balanced %.1f %% Of The Time", parseJSONDouble("percentBalance")));

            _percentLift.setText(String.format("Lifts Others %.1f %% Of The Time", parseJSONDouble("percentLift")));

            _penalties.setText(String.format("Gets An Average Of %.1f Penalties Per Game", parseJSONDouble("penalties")));

            _comments.setText("Extra Comments: " + parseJSONString("comments"));

        }catch (Exception e){
            giveErrorCode(MatchUpdateError);
        }

    }

    public String parseJSONString(String value){

        String returnVal = "";

        try {

            if(jsonObj != null) {

                returnVal = jsonObj.getString(value);

            }

        }catch (Exception e){

            giveErrorText(e.toString());

            returnVal = "N/A";

        }

        return returnVal;

    }

    public Integer parseJSONInt(String value){

        int returnVal = 0;

        try {

            if(jsonObj != null) {

                returnVal = jsonObj.getInt(value);

            }

        }catch (Exception e){

            giveErrorText(e.toString());

            returnVal = 0;

        }

        return returnVal;

    }

    public Double parseJSONDouble(String value){

        Double returnVal = 0.0;

        try {

            if(jsonObj != null) {

                returnVal = jsonObj.getDouble(value);

            }

        }catch (Exception e){

            giveErrorText(e.toString());

            returnVal = 0.0;

        }

        return returnVal;

    }

    public void giveErrorCode(int codeNum){

        String message = "Error; Code " + codeNum;

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }

    public void giveErrorText(String phrase){

        String message = "Error; Code " + phrase;

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }

    public void giveSuccessMessage(){

        Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_LONG).show();

    }

}

class getJSON extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... myURL) {
        String response = "";

        try {
            URL url = new URL(myURL[0]);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            try {
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                byte[] contents = new byte[1024];

                int bytesRead = 0;
                while((bytesRead = is.read(contents)) != -1) {
                    response += new String(contents, 0, bytesRead);
                }
            }
            catch(Exception e) {
                response = "E - " + e.toString();
            }
            finally {
                urlConnection.disconnect();
            }

//            return new JSONObject(response);
            return response;

        } catch (Exception ex) {

            //ex.printStackTrace();

        }

        return null;
    }


    @Override
    protected void onPostExecute(String result){

        super.onPostExecute(result);
    }

}