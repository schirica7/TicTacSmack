package com.jonesclass.chirica.tictacsmack;

/*This project was created for educational purposes while programming at Sylvania Southview.
Copyright (c) 2022 by Stefan Chirica.
All rights reserved.*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

//Return an image
public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivityTag";
    ImageButton[][] imageButtons = new ImageButton[3][3];
    TextView messageTextView;
    TextView windowsScoreTextView;
    TextView appleScoreTextView;
    ArrayList<String> player1Clicks = new ArrayList<>();
    ArrayList<String> player2Clicks = new ArrayList<>();
    Intent gameIntent;
    Random random;

    String teamChoice;
    int clicks = 0;
    int initialClicks;
    int player1Score = 0;
    int player2Score = 0;
    boolean onePlayer;
    boolean noOneWon = true;
    boolean firstGame = true;
    boolean aiMoved = false;
    boolean player1Win;
    boolean player2Win;
    boolean allSquaresFilled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameIntent = getIntent();
        onePlayer = gameIntent.getBooleanExtra("com.jonesclass.chirica.tictacsmack.ONE_PLAYER", false);
        random = new Random();
        clicks = random.nextInt(2);
        initialClicks = clicks;

        messageTextView = findViewById(R.id.textView_message);
        windowsScoreTextView = findViewById(R.id.textView_score1);
        appleScoreTextView = findViewById(R.id.textView_score2);

        imageButtons[0][0] = findViewById(R.id.imageButton_00);
        imageButtons[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(0, 0);
            }
        });

        imageButtons[0][1] = findViewById(R.id.imageButton_01);
        imageButtons[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(0, 1);
            }
        });

        imageButtons[0][2] = findViewById(R.id.imageButton_02);
        imageButtons[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(0, 2);
            }
        });

        imageButtons[1][0] = findViewById(R.id.imageButton_10);
        imageButtons[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(1, 0);
            }
        });

        imageButtons[1][1] = findViewById(R.id.imageButton_11);
        imageButtons[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(1, 1);
            }
        });

        imageButtons[1][2] = findViewById(R.id.imageButton_12);
        imageButtons[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(1, 2);
            }
        });

        imageButtons[2][0] = findViewById(R.id.imageButton_20);
        imageButtons[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(2, 0);
            }
        });

        imageButtons[2][1] = findViewById(R.id.imageButton_21);
        imageButtons[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(2, 1);
            }
        });

        imageButtons[2][2] = findViewById(R.id.imageButton_22);
        imageButtons[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(2, 2);
            }
        });

        Button newGameButton = findViewById(R.id.button_newGame);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        imageButtons[i][j].setImageDrawable(getResources().getDrawable(R.drawable.blank));
                    }
                }

                messageTextView.setText("");
                changeClickability(true);

                // Alternating who goes first
                noOneWon = true;
                player1Clicks = new ArrayList<>();
                player2Clicks = new ArrayList<>();

                if (initialClicks == 1) {
                    clicks = 0;

                    if (!onePlayer) {
                        teamChoice = "Windows";
                    }

                    initialClicks = clicks;
                } else {
                    clicks = 1;

                    if (!onePlayer) {
                        teamChoice = "Apple";
                    }

                    initialClicks = clicks;

                    if (onePlayer) {
                        aiMove();
                    }
                }
                 changeMessageText();
            }
        });

        Button mainMenuButton = findViewById(R.id.button_mainMenu);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (onePlayer && firstGame) {
            teamChoice = gameIntent.getStringExtra("com.jonesclass.chirica.tictacsmack.TEAM");
            if (initialClicks % 2 == 1) {
                aiMove();
                firstGame = false;
            }
        }

        if (!onePlayer && initialClicks == 0) {
            teamChoice = "Windows";
        } else if (!onePlayer && initialClicks == 1) {
            teamChoice = "Apple";
        }

        changeMessageText();
    }

    private void buttonClicked(int row, int column) {
        String image = String.valueOf(row) + String.valueOf(column);
        aiMoved = false;

        if (noOneWon) {
            if (!player1Clicks.contains(image)
                    && !player2Clicks.contains(image)) {
                if (clicks % 2 == 0) {
                    if (onePlayer) {
                        if (teamChoice.equals("Windows")) {
                            imageButtons[row][column].setImageDrawable(getResources().getDrawable(R.drawable.windows));
                        } else {
                            imageButtons[row][column].setImageDrawable(getResources().getDrawable(R.drawable.apple));
                        }
                    } else {
                        imageButtons[row][column].setImageDrawable(getResources().getDrawable(R.drawable.windows));
                    }
                } else {
                    if (onePlayer) {
                        if (teamChoice.equals("Windows")) {
                            imageButtons[row][column].setImageDrawable(getResources().getDrawable(R.drawable.apple));
                        } else {
                            imageButtons[row][column].setImageDrawable(getResources().getDrawable(R.drawable.windows));
                        }
                    } else {
                        imageButtons[row][column].setImageDrawable(getResources().getDrawable(R.drawable.apple));
                    }
                }
                addClick(row, column);
            }
            if (noOneWon) {
                checkWinner();
            }
        }
    }

    private void addClick(int row, int column) {
        String buttonIndex = String.valueOf(row) + String.valueOf(column);

        if (noOneWon) {
            if (clicks % 2 == 0) {
                player1Clicks.add(buttonIndex);
            } else {
                player2Clicks.add(buttonIndex);
            }
            checkWinner();

            if (noOneWon) {
                clicks++;
                changeMessageText();

                if (onePlayer && clicks % 2 == 1) {
                    if (!allSquaresFilled) {
                        aiMove();
                    }
                }
            }
        }
    }

    private void checkWinner() {
        /*
        Horizontal
        00, 01, 02
        10, 11, 12
        20, 21, 22

        Vertical
        00, 10, 20
        01, 11, 21
        02, 12, 22

        Diagonal
        00, 11, 22
        02, 11, 20
         */

         player1Win = (player1Clicks.contains("00") && player1Clicks.contains("01") && player1Clicks.contains("02"))
                || (player1Clicks.contains("10") && player1Clicks.contains("11") && player1Clicks.contains("12"))
                || (player1Clicks.contains("20") && player1Clicks.contains("21") && player1Clicks.contains("22"))
                || (player1Clicks.contains("00") && player1Clicks.contains("10") && player1Clicks.contains("20"))
                || (player1Clicks.contains("01") && player1Clicks.contains("11") && player1Clicks.contains("21"))
                || (player1Clicks.contains("02") && player1Clicks.contains("12") && player1Clicks.contains("22"))
                || (player1Clicks.contains("00") && player1Clicks.contains("11") && player1Clicks.contains("22"))
                || (player1Clicks.contains("02") && player1Clicks.contains("11") && player1Clicks.contains("20"));

         player2Win = (player2Clicks.contains("00") && player2Clicks.contains("01") && player2Clicks.contains("02"))
                || (player2Clicks.contains("10") && player2Clicks.contains("11") && player2Clicks.contains("12"))
                || (player2Clicks.contains("20") && player2Clicks.contains("21") && player2Clicks.contains("22"))
                || (player2Clicks.contains("00") && player2Clicks.contains("10") && player2Clicks.contains("20"))
                || (player2Clicks.contains("01") && player2Clicks.contains("11") && player2Clicks.contains("21"))
                || (player2Clicks.contains("02") && player2Clicks.contains("12") && player2Clicks.contains("22"))
                || (player2Clicks.contains("00") && player2Clicks.contains("11") && player2Clicks.contains("22"))
                || (player2Clicks.contains("02") && player2Clicks.contains("11") && player2Clicks.contains("20"));

         allSquaresFilled = (player1Clicks.contains("00") || player2Clicks.contains("00"))
                && (player1Clicks.contains("01") || player2Clicks.contains("01"))
                && (player1Clicks.contains("02") || player2Clicks.contains("02"))
                && (player1Clicks.contains("10") || player2Clicks.contains("10"))
                && (player1Clicks.contains("11") || player2Clicks.contains("11"))
                && (player1Clicks.contains("12") || player2Clicks.contains("12"))
                && (player1Clicks.contains("20") || player2Clicks.contains("20"))
                && (player1Clicks.contains("21") || player2Clicks.contains("21"))
                && (player1Clicks.contains("22") || player2Clicks.contains("22"));


        if (player1Win)  {
            player1Score++;

            if (!onePlayer) {
                messageTextView.setText(teamChoice + " Wins!");
            } else {
                if (teamChoice.equals("Windows")) {
                    messageTextView.setText("Windows wins!");
                } else {
                    messageTextView.setText("Apple wins!");
                }
            }

            changeClickability(false);
            noOneWon = false;

            if (!onePlayer) {
                windowsScoreTextView.setText("Windows: " + String.valueOf(player1Score));
            } else {
                if (teamChoice.equals("Windows")) {
                    windowsScoreTextView.setText("Windows: " + String.valueOf(player1Score));
                } else {
                    appleScoreTextView.setText("Apple: " + String.valueOf(player1Score));
                }
            }
        }

        if (player2Win)  {
            player2Score++;

            if (!onePlayer) {
                messageTextView.setText(teamChoice + " Wins!");
            }  else {
                if (teamChoice.equals("Windows")) {
                    messageTextView.setText("Apple wins!");
                } else {
                    messageTextView.setText("Windows wins!");
                }
            }

            changeClickability(false);
            noOneWon = false;

            if (!onePlayer) {
                appleScoreTextView.setText("Apple: " + String.valueOf(player2Score));
            } else {
                if (teamChoice.equals("Windows")) {
                    appleScoreTextView.setText("Apple: " + String.valueOf(player2Score));
                } else {
                    windowsScoreTextView.setText("Windows: " + String.valueOf(player2Score));
                }
            }
        }

        if (allSquaresFilled && !player1Win && !player2Win) {
            messageTextView.setText("Draw!");
            changeClickability(false);
        }
    }

    private void changeClickability(Boolean canClick) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                imageButtons[i][j].setClickable(canClick);
            }
        }
    }

    private void changeMessageText() {
        if (onePlayer) {
            if (teamChoice.equals("Windows")) {
                messageTextView.setText("Windows, it is now your turn!");
            } else {
                messageTextView.setText("Apple, it is now your turn!");
            }
        } else {
            if (clicks % 2 == 0) {
                messageTextView.setText("Windows, it is now your turn!");
            } else {
                messageTextView.setText("Apple, it is now your turn!");
            }
        }
    }

    private void aiMove() {
        int row;
        int column;

        do {
            row = random.nextInt(3);
            column = random.nextInt(3);
        } while (player1Clicks.contains(String.valueOf(row) + String.valueOf(column))
                || player2Clicks.contains(String.valueOf(row) + String.valueOf(column)));

        aiMoved = true;
        buttonClicked(row, column);
    }
}