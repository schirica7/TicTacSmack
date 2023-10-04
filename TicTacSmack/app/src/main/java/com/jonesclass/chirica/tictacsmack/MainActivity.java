package com.jonesclass.chirica.tictacsmack;

/*This project was created for educational purposes while programming at Sylvania Southview.
Copyright (c) 2022 by Stefan Chirica.
All rights reserved.*/

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityTag";
    AlertDialog.Builder dialogBuilder;
    String teamChoice = "";
    Button startButton;
    Button teamButton;
    RadioButton onePlayerRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.button_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Success");
                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                gameIntent.putExtra("com.jonesclass.chirica.tictacsmack.TEAM", teamChoice);

               onePlayerRadioButton = findViewById(R.id.radioButton_1Player);

                if (onePlayerRadioButton.isChecked()) {
                    gameIntent.putExtra("com.jonesclass.chirica.tictacsmack.ONE_PLAYER", true);
                } else {
                    gameIntent.putExtra("com.jonesclass.chirica.tictacsmack.ONE_PLAYER", false);
                }

                //gameIntent.p

                startActivity(gameIntent);
            }
        });

        teamButton = findViewById(R.id.button_selectTeam);
        teamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTeamDialog();
            }
        });

        RadioGroup playersRadioGroup = findViewById(R.id.radioGroup_players);
        playersRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButton_2Player) {
                    startButton.setVisibility(View.VISIBLE);
                    teamButton.setVisibility(View.GONE);
                } else {
                    startButton.setVisibility(View.GONE);
                    teamButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void selectTeamDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final String[] TEAM_CHOICES = {"Windows", "Apple"};
        dialogBuilder.setTitle("Choose Your Team!!");

        dialogBuilder.setSingleChoiceItems(TEAM_CHOICES, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichOne) {
                teamChoice = TEAM_CHOICES[whichOne];

                if (teamChoice.equals("Windows")) {
                    Toast.makeText(MainActivity.this, "You now have a window into my soul...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "You're the apple of my eye :)", Toast.LENGTH_SHORT).show();
                }

                startButton.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
            }
        });
        
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int clicked) {
                Toast.makeText(MainActivity.this, "No Team Selected!", Toast.LENGTH_SHORT).show();
            }
        });
        
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}