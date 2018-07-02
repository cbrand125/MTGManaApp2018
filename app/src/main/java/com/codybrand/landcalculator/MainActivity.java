package com.codybrand.landcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
	
	public final static String EXTRA_DECK_SIZE = "com.codybrand.landcalculator.DECK_SIZE";
	public final static String EXTRA_USABLE_SOURCES = "com.codybrand.landcalculator.USABLE_SOURCES";
	public final static String EXTRA_BY_TURN = "com.codybrand.landcalculator.BY_TURN";
	public final static String EXTRA_ACHIEVE_CHANCE = "com.codybrand.landcalculator.ACHIEVE_CHANCE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initSeekBar(COLORS.red);
	}
	
	/** Called when the user clicks the Submit button */
	public void submitInput(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        RadioButton deckSize40Btn = findViewById(R.id.deckSize40);
        RadioButton deckSize60Btn = findViewById(R.id.deckSize60);
        RadioButton deckSize100Btn = findViewById(R.id.deckSize100);
        SeekBar redSourcesSeekBar = findViewById(R.id.redSourcesSeekBar);
        SeekBar redTurnSeekBar = findViewById(R.id.redTurnSeekBar);

        int deckSize;
        int redSources;
        int redTurn;
        double achieveChance;

        redSources = redSourcesSeekBar.getProgress();
        redTurn = redTurnSeekBar.getProgress();
        achieveChance = .84;

        if (deckSize40Btn.isChecked()) {
            deckSize = 40;
        } else if (deckSize60Btn.isChecked()) {
            deckSize = 60;
        } else if (deckSize100Btn.isChecked()) {
            deckSize = 100;
        } else {
            deckSize = 60;
        }
		
		intent.putExtra(EXTRA_DECK_SIZE, deckSize);
		intent.putExtra(EXTRA_USABLE_SOURCES, redSources);
		intent.putExtra(EXTRA_BY_TURN, redTurn);
		intent.putExtra(EXTRA_ACHIEVE_CHANCE, achieveChance);
		
		startActivity(intent);
	}
	
	/** Called when the user clicks the Reset button */
	public void resetInput(View view) {
		RadioButton deckSize60Btn = findViewById(R.id.deckSize60);
        SeekBar redSourcesSeekBar = findViewById(R.id.redSourcesSeekBar);
        SeekBar redTurnSeekBar = findViewById(R.id.redTurnSeekBar);
		
		deckSize60Btn.setChecked(true);
		redSourcesSeekBar.setProgress(0);
		redTurnSeekBar.setProgress(0);
	}
	
	/** Called when the user clicks the Help button */
	public void helpPage(View view) {
		Intent intent = new Intent(this, HelpPageActivity.class);
		startActivity(intent);
	}

	public void initSeekBar(COLORS color) {
	    int seekBarID;
	    int turnSeekBarID;
	    int textID;
	    int turnTextID;
	    switch (color) {
            case red:
                seekBarID = R.id.redSourcesSeekBar;
                turnSeekBarID = R.id.redTurnSeekBar;
                textID = R.id.redSources;
                turnTextID = R.id.redTurn;
                break;
            default:
                seekBarID = R.id.redSourcesSeekBar;
                turnSeekBarID = R.id.redTurnSeekBar;
                textID = R.id.redSources;
                turnTextID = R.id.redTurn;
                break;
        }
        final SeekBar seekbar = (SeekBar) findViewById(seekBarID);
        final SeekBar turnSeekbar = (SeekBar) findViewById(turnSeekBarID);
        final TextView text = (TextView) findViewById(textID);
        final TextView turnText = (TextView) findViewById(turnTextID);
        text.setText("0");
        turnText.setText("0");
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                text.setText(progress + "");
                if (turnSeekbar.getProgress() < progress) {
                    turnSeekbar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        turnSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                turnText.setText(progress + "");
                if (seekbar.getProgress() > progress) {
                    seekbar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
