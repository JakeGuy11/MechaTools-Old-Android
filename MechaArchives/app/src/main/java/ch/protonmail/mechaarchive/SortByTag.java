package ch.protonmail.mechaarchive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class SortByTag extends AppCompatActivity {

    //Objects
    Button _exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_by_tag);

        _exitButton = (Button) findViewById(R.id.dExitButton);

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
    }
}
