package ch.protonmail.a7722mechascouting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.view.View;

public class InputSpecs extends AppCompatActivity {

    //Variables

    //Objects
    Button _specsExit;
    Button _saveAndPush;
    EditText _teamNum;
    EditText _heightInches;
    EditText _weight;
    RadioButton _codeCPP;
    RadioButton _codeJava;
    RadioButton _codePython;
    RadioButton _codeOther;
    RadioButton _experience1;
    RadioButton _experience2;
    RadioButton _experience3;
    RadioButton _experience4;
    CheckBox _powerPort1;
    CheckBox _hasAuto;
    CheckBox _shootsAuto;
    CheckBox _picksAuto;
    CheckBox _powerPort2;
    CheckBox _powerPort3;
    CheckBox _spinWheel;
    CheckBox _colorWheel;
    CheckBox _underWheel;
    CheckBox _climb;
    CheckBox _canBalance;
    CheckBox _liftBots;
    EditText _extraComments;

    //Storage Variables

    String teamNum;
    String height;
    String weight;
    String langCPP;
    String langJava;
    String langPy;
    String langOther;
    String hasAuto;
    String shootsAuto;
    String picksAuto;
    String exp1;
    String exp2;
    String exp3;
    String exp4;
    String power1;
    String power2;
    String power3;
    String spinWheel;
    String colorWheel;
    String underWheel;
    String climb;
    String balanced;
    String liftedOthers;
    String notes;

    //Error Codes
    int ElementCheckError = 1;
    int GeneralWebError = 2;
    int UrlGenerationError = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_specs);

        _specsExit = (Button) findViewById(R.id.dSpecsExit);
        _saveAndPush = (Button) findViewById(R.id.dSpecsSave);
        _teamNum = (EditText) findViewById(R.id.dSpecsTeam);
        _heightInches = (EditText) findViewById(R.id.dSpecsInches);
        _weight = (EditText) findViewById(R.id.dSpecsWeightInput);
        _codeCPP = (RadioButton) findViewById(R.id.dSpecsLanguageC);
        _codeJava = (RadioButton) findViewById(R.id.dSpecsLanguageJava);
        _codePython = (RadioButton) findViewById(R.id.dSpecsLanguagePython);
        _codeOther = (RadioButton) findViewById(R.id.dSpecsLanguageOther);
        _experience1 = (RadioButton) findViewById(R.id.dSpecsExperienceNew);
        _experience2 = (RadioButton) findViewById(R.id.dSpecsExperience1);
        _experience3 = (RadioButton) findViewById(R.id.dSpecsExperience2);
        _experience4 = (RadioButton) findViewById(R.id.dSpecsExperience3);
        _hasAuto = (CheckBox) findViewById(R.id.dHasAuto);
        _shootsAuto = (CheckBox) findViewById(R.id.dShootsInAuto);
        _picksAuto = (CheckBox) findViewById(R.id.dReloadsInAuto);
        _powerPort1 = (CheckBox) findViewById(R.id.dSpecsAbilitiesCellBottom);
        _powerPort2 = (CheckBox) findViewById(R.id.dSpecsAbilitiesCellHex);
        _powerPort3 = (CheckBox) findViewById(R.id.dSpecsAbilitiesCellBullseye);
        _spinWheel = (CheckBox) findViewById(R.id.dSpecsAbilitiesWheelSpin);
        _colorWheel = (CheckBox) findViewById(R.id.dSpecsAbilitiesWheelColor);
        _underWheel = (CheckBox) findViewById(R.id.dSpecsAbilitiesWheelUnder);
        _climb = (CheckBox) findViewById(R.id.dSpecsAbilitiesClimb);
        _canBalance = (CheckBox) findViewById(R.id.dSpecsAbilitiesClimbBalance);
        _liftBots = (CheckBox) findViewById(R.id.dSpecsAbilitiesClimbLift);
        _extraComments = (EditText) findViewById(R.id.dSpecsCommentsInput);

        CheckForLiveEvents();

    }

    public void CheckForLiveEvents() {

        _specsExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }});

        _saveAndPush.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                UpdateElementValues();

                String urlString = "http://7722.ca/record_spec.php?";

                try {

                    urlString += "team=LT" + teamNum;
                    urlString += "&height=" + height;
                    urlString += "&weight=" + weight;
                    urlString += "&DesignedInAuto=" + hasAuto;
                    urlString += "&ShootsInAuto=" + shootsAuto;
                    urlString += "&PicksUpInAuto=" + picksAuto;
                    urlString += "&CanDoBase=" + power1;
                    urlString += "&CanDoHex=" + power2;
                    urlString += "&CanDoBullseye=" + power3;
                    urlString += "&CanDoWheelSpin=" + spinWheel;
                    urlString += "&CanDoColourWheel=" + colorWheel;
                    urlString += "&CanGoUnderWheel=" + underWheel;
                    urlString += "&CanClimbLevel1=" + climb;
                    urlString += "&CanBalance=" + balanced;
                    urlString += "&CanLiftOtherBots=" + liftedOthers;
                    urlString += "&Notes=" + notes;

                    if (exp2 == "1") {
                        urlString += "&DriverExperience=2";
                    } else if (exp3 == "1") {
                        urlString += "&DriverExperience=3";
                    } else if (exp4 == "1") {
                        urlString += "&DriverExperience=4";
                    } else {
                        urlString += "&DriverExperience=1";
                    }

                    if (langCPP == "1") {
                        urlString += "&CodingLanguage=1";
                    } else if (langJava == "1") {
                        urlString += "&CodingLanguage=2";
                    } else if (langPy == "1") {
                        urlString += "&CodingLanguage=3";
                    } else {
                        urlString += "&CodingLanguage=4";
                    }

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
    }

    public void UpdateElementValues() {

        try {

            teamNum = _teamNum.getText().toString();

            height = _heightInches.getText().toString();

            weight = _weight.getText().toString();

            notes = _extraComments.getText().toString();

            if (_codeCPP.isChecked()) {
                langCPP = "1";
            } else {
                langCPP = "0";
            }

            if (_codeJava.isChecked()) {
                langJava = "1";
            } else {
                langJava = "0";
            }

            if (_codePython.isChecked()) {
                langPy = "1";
            } else {
                langPy = "0";
            }

            if (_codeOther.isChecked()) {
                langOther = "1";
            } else {
                langOther = "0";
            }

            exp1 = "0";
            exp2 = "0";
            exp3 = "0";
            exp4 = "0";
            if (_experience1.isChecked()) {
                exp1 = "1";
            }

            if (_experience2.isChecked()) {
                exp2 = "1";
            }

            if (_experience3.isChecked()) {
                exp3 = "1";
            }

            if (_experience4.isChecked()) {
                exp4 = "1";
            }

            if (_hasAuto.isChecked()) {
                hasAuto = "1";
            } else {
                hasAuto = "0";
            }

            if (_shootsAuto.isChecked()) {
                shootsAuto = "1";
            } else {
                shootsAuto = "0";
            }

            if (_picksAuto.isChecked()) {
                picksAuto = "1";
            } else {
                picksAuto = "0";
            }

            if (_powerPort1.isChecked()) {
                power1 = "1";
            } else {
                power1 = "0";
            }

            if (_powerPort2.isChecked()) {
                power2 = "1";
            } else {
                power2 = "0";
            }

            if (_powerPort3.isChecked()) {
                power3 = "1";
            } else {
                power3 = "0";
            }

            if (_spinWheel.isChecked()) {
                spinWheel = "1";
            } else {
                spinWheel = "0";
            }

            if (_colorWheel.isChecked()) {
                colorWheel = "1";
            } else {
                colorWheel = "0";
            }

            if (_underWheel.isChecked()) {
                underWheel = "1";
            } else {
                underWheel = "0";
            }

            if (_climb.isChecked()) {
                climb = "1";
            } else {
                climb = "0";
            }

            if (_canBalance.isChecked()) {
                balanced = "1";
            } else {
                balanced = "0";
            }

            if (_liftBots.isChecked()) {
                liftedOthers = "1";
            } else {
                liftedOthers = "0";
            }

        }catch (Exception e){
            giveErrorCode(ElementCheckError);
        }

    }

    public void giveErrorCode(int codeNum){

        String message = "Error; Code " + Integer.toString(codeNum);

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }

    public void giveErrorText(String phrase){

        String message = "Error; Code " + phrase;

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }

    public void giveSuccessMessage(){

        Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_SHORT).show();

    }
}