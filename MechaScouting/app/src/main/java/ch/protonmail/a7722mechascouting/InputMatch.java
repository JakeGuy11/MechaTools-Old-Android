package ch.protonmail.a7722mechascouting;

import java.io.*;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.view.View;
import java.net.*;

import android.os.AsyncTask;

public class InputMatch extends AppCompatActivity {

    //Objects

    //Non-Scoring Objects
    LinearLayout _rootContainer;
    Button _exitButton;
    Button _saveButton;
    Switch _redMode;
    EditText _extraComments;
    EditText _weekInput;
    EditText _matchInput;
    EditText _teamInput;

    //Auto Objects
    Button _autoBottomPlus;
    Button _autoHexPlus;
    Button _autoBullseyePlus;
    RadioButton _autoNothing;
    RadioButton _autoMoved;

    //Teleop Objects
    Button _teleopBottomPlus;
    Button _teleopHexPlus;
    Button _teleopBullseyePlus;
    CheckBox _wheelSpun;
    CheckBox _colorWheel;
    RadioButton _noEndgame;
    RadioButton _lift;
    CheckBox _finishedBalanced;
    CheckBox _liftedOthers;

    //Penalty Objects
    Button _penaltiesPlus;

    //Storage Variables

    //Non-Scoring Storage Variables
    String userComments;
    String week;
    String match;
    String teamNum;

    //Auto Storage Variables
    int autoBottomPlusCounter = 0;
    int autoHexPlusCounter = 0;
    int autoBullseyePlusCounter = 0;
    String yesAuto;

    //Teleop Storage Variables
    int teleopBottomPlusCounter = 0;
    int teleopHexPlusCounter = 0;
    int teleopBullseyePlusCounter = 0;
    String wheelSpun;
    String wheelColored;
    String noClimb;
    String lift;
    String balanceEndgame;
    String liftedOthers;

    //Penalty Storage Variables
    int penaltiesPlusCounter;

    //Error Codes

    int ElementCheckError = 1;
    int GeneralWebError = 2;
    int UrlGenerationError = 3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_match);

        //Non-Scoring Features
        _rootContainer = (LinearLayout) findViewById(R.id.dRootContainer);
        _exitButton = (Button) findViewById(R.id.dExitButton);
        _saveButton = (Button) findViewById(R.id.dSaveButton);
        _redMode = (Switch) findViewById(R.id.dRedMode);
        _weekInput = (EditText) findViewById(R.id.dweekNum);
        _matchInput = (EditText) findViewById(R.id.dmatchNum);
        _teamInput = (EditText) findViewById(R.id.dteamNum);
        _extraComments = (EditText) findViewById(R.id.dMatchCommentsInput);

        //Auto Features
        _autoBottomPlus = (Button) findViewById(R.id.dAutoBottomPlus);
        _autoHexPlus = (Button) findViewById(R.id.dAutoHexPlus);
        _autoBullseyePlus = (Button) findViewById(R.id.dAutoBullseyePlus);
        _autoNothing = (RadioButton) findViewById(R.id.dAutoNothing);
        _autoMoved = (RadioButton) findViewById(R.id.dAutoMoved);

        //Teleop Features
        _teleopBottomPlus = (Button) findViewById(R.id.dTeleopBottomPlus);
        _teleopHexPlus = (Button) findViewById(R.id.dTeleopHexPlus);
        _teleopBullseyePlus = (Button) findViewById(R.id.dTeleopBullseyePlus);
        _wheelSpun = (CheckBox) findViewById(R.id.dTeleopControlPanelSpin);
        _colorWheel = (CheckBox) findViewById(R.id.dTeleopControlPanelColor);
        _noEndgame = (RadioButton) findViewById(R.id.dTeleopNoClimb);
        _lift = (RadioButton) findViewById(R.id.dTeleopLift);
        _finishedBalanced = (CheckBox) findViewById(R.id.dTeleopClimbBalanced);
        _liftedOthers = (CheckBox) findViewById(R.id.dTeleopClimbLiftOthers);

        //Penalty Features
        _penaltiesPlus = (Button) findViewById(R.id.dPenaltiesPlus);

        CheckForLiveEvents();

    }

    public void CheckForLiveEvents() {

        //Exit Button Checker
        _exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }});

        _saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                UpdateElementValues();

                String urlString = "http://7722.ca/record_match.php?team=LT";

                try {

                    match = "W" + week + "Q" + match;

                    urlString += teamNum;
                    urlString += "&Match=" + match;
                    urlString += "&Notes=" + userComments;
                    urlString += "&AutoMoved=" + yesAuto;
                    urlString += "&AutoBase=" + autoBottomPlusCounter;
                    urlString += "&AutoHex=" + autoHexPlusCounter;
                    urlString += "&AutoBullseye=" + autoBullseyePlusCounter;
                    urlString += "&TeleBase=" + teleopBottomPlusCounter;
                    urlString += "&TeleHex=" + teleopHexPlusCounter;
                    urlString += "&TeleBullseye=" + teleopBullseyePlusCounter;
                    urlString += "&SpunWheelSuccess=" + wheelSpun;
                    urlString += "&ColorWheelSuccess=" + wheelColored;
                    urlString += "&Balance=" + balanceEndgame;
                    urlString += "&LiftedOtherBotsSuccess=" + liftedOthers;
                    urlString += "&Penalties=" + penaltiesPlusCounter;
                    urlString += "&Climb=" + lift;
                    urlString += "&NoEndgame=" + noClimb;


                }catch (Exception e){
                    giveErrorCode(UrlGenerationError);
                }

                try {

                    UploadMatchData myData = new UploadMatchData();

                    myData.execute(urlString);

                    giveSuccessMessage();

                }catch (Exception e){

                    giveErrorCode(GeneralWebError);

                }

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }});

        //Red Mode Checker
        _redMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    _rootContainer.setBackgroundColor(getResources().getColor(R.color.colorAllianceRed));
                } else {
                    _rootContainer.setBackgroundColor(getResources().getColor(R.color.colorAllianceBlue));
                }
            }
        });

    }

    public void UpdateElementValues() {

        try {

            week = _weekInput.getText().toString();

            match = _matchInput.getText().toString();

            teamNum = _teamInput.getText().toString();

            userComments = _extraComments.getText().toString();

            if (_autoMoved.isChecked()) {
                yesAuto = "1";
            } else {
                yesAuto = "0";
            }

            if (_wheelSpun.isChecked()) {
                wheelSpun = "1";
            } else {
                wheelSpun = "0";
            }

            if (_colorWheel.isChecked()) {
                wheelColored = "1";
            } else {
                wheelColored = "0";
            }

            if (_noEndgame.isChecked()) {
                noClimb = "1";
            } else {
                noClimb = "0";
            }

            if (_lift.isChecked()) {
                lift = "1";
            } else{
                lift = "0";
            }

            if (_finishedBalanced.isChecked()) {
                balanceEndgame = "1";
            } else {
                balanceEndgame = "0";
            }

            if (_liftedOthers.isChecked()) {
                liftedOthers = "1";
            } else {
                liftedOthers = "0";
            }

        }catch (Exception e){

            giveErrorCode(ElementCheckError);

        }
    }

    public void AutoBottomPlus(android.view.View view1) {
        autoBottomPlusCounter++;
        _autoBottomPlus.setText(String.valueOf(autoBottomPlusCounter));

    }

    public void AutoBottomMinus(android.view.View view1) {
        autoBottomPlusCounter--;
        if (autoBottomPlusCounter <= 0) autoBottomPlusCounter = 0;
        _autoBottomPlus.setText(String.valueOf(autoBottomPlusCounter));

    }

    public void AutoHexPlus(android.view.View view1) {
        autoHexPlusCounter++;
        _autoHexPlus.setText(String.valueOf(autoHexPlusCounter));

    }

    public void AutoHexMinus(android.view.View view1) {
        autoHexPlusCounter--;
        if (autoHexPlusCounter <= 0) autoHexPlusCounter = 0;
        _autoHexPlus.setText(String.valueOf(autoHexPlusCounter));

    }

    public void AutoBullseyePlus(android.view.View view1) {
        autoBullseyePlusCounter++;
        _autoBullseyePlus.setText(String.valueOf(autoBullseyePlusCounter));

    }

    public void AutoBullseyeMinus(android.view.View view1) {
        autoBullseyePlusCounter--;
        if (autoBullseyePlusCounter <= 0) autoBullseyePlusCounter = 0;
        _autoBullseyePlus.setText(String.valueOf(autoBullseyePlusCounter));

    }

    public void TeleopBottomPlus(android.view.View view1) {
        teleopBottomPlusCounter++;
        _teleopBottomPlus.setText(String.valueOf(teleopBottomPlusCounter));

    }

    public void TeleopBottomMinus(android.view.View view1) {
        teleopBottomPlusCounter--;
        if (teleopBottomPlusCounter <= 0) teleopBottomPlusCounter = 0;
        _teleopBottomPlus.setText(String.valueOf(teleopBottomPlusCounter));

    }

    public void TeleopHexPlus(android.view.View view1) {
        teleopHexPlusCounter++;
        _teleopHexPlus.setText(String.valueOf(teleopHexPlusCounter));

    }

    public void TeleopHexMinus(android.view.View view1) {
        teleopHexPlusCounter--;
        if (teleopHexPlusCounter <= 0) teleopHexPlusCounter = 0;
        _teleopHexPlus.setText(String.valueOf(teleopHexPlusCounter));

    }

    public void TeleopBullseyePlus(android.view.View view1) {
        teleopBullseyePlusCounter++;
        _teleopBullseyePlus.setText(String.valueOf(teleopBullseyePlusCounter));

    }

    public void TeleopBullseyeMinus(android.view.View view1) {
        teleopBullseyePlusCounter--;
        if (teleopBullseyePlusCounter <= 0) teleopBullseyePlusCounter = 0;
        _teleopBullseyePlus.setText(String.valueOf(teleopBullseyePlusCounter));

    }

    public void PenaltiesPlus(android.view.View view1) {
        penaltiesPlusCounter++;
        if (penaltiesPlusCounter <= 0) penaltiesPlusCounter = 0;
        _penaltiesPlus.setText(String.valueOf(penaltiesPlusCounter));

    }

    public void PenaltiesMinus(android.view.View view1) {
        penaltiesPlusCounter--;
        if (penaltiesPlusCounter <= 0) penaltiesPlusCounter = 0;
        _penaltiesPlus.setText(String.valueOf(penaltiesPlusCounter));

    }

    public void giveErrorCode(int codeNum){

        String message = "Error; Code " + codeNum;

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }

    public void giveSuccessMessage(){

        Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_SHORT).show();

    }

}

class UploadMatchData extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... updateUrl) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String myString = updateUrl[0];

        String updateResult;

        try {
            // the URL will be passed to us fully formed with all parameters, IP and port
            URL url = new URL(myString);

            // Create the request to our wordpress server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // this puts the website returned webpage into a string named buffer
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            // check for success
            if (buffer.indexOf("Success") != -1) {
                updateResult = "Success";
            } else {
                updateResult = "Failed";
            }
            return updateResult;
        } catch (IOException e) {
            //Log.e("IOException", "Error ", e);
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    //Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }

    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // here you can set the text returned into either the button text and then exit or
        // the little text box under, or use the cool message thing
        //_saveButton.setText(s);
    }
}