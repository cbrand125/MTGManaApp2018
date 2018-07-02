package com.codybrand.landcalculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		Intent intent = getIntent();
		int deckSize = intent.getIntExtra(MainActivity.EXTRA_DECK_SIZE, 60);
		int usableSources = intent.getIntExtra(MainActivity.EXTRA_USABLE_SOURCES, 1);
		int byTurn = intent.getIntExtra(MainActivity.EXTRA_BY_TURN, 1);
		double achieveChance = intent.getDoubleExtra(MainActivity.EXTRA_ACHIEVE_CHANCE, .84);
		ArrayList<LandSuggestion> landCombos = new ArrayList<LandSuggestion>();
		TreeMap<Integer, Integer> landCombosMap = new TreeMap<Integer, Integer>();
		StringBuilder message = new StringBuilder();

		if (deckSize > 0 && usableSources > 0 && byTurn > 0) {
			landCombos = LandComboFactory.findAllLandCombos(deckSize, achieveChance, usableSources, byTurn, landCombosMap);
		}

		message.append("Play with the slider below to see each combination of land that enters tapped and land that enters untapped that will ensure an ");
		message.append(achieveChance*100);
		message.append("% chance of having ");
		message.append(usableSources);
		message.append(" available source(s) on turn ");
		message.append(byTurn);
		message.append(" using ");
		message.append(deckSize/2);
		message.append(" lands or less");
		message.append(" for a deck size of ");
		message.append(deckSize);
		message.append(":");
		
		Iterator landCombosIterator = landCombos.iterator();
		if (landCombosIterator.hasNext()) {
            initComboSeekBar(COLORS.red, landCombosMap);
		} else {
		    hideSeekbar(COLORS.red);
		    message.append(System.getProperty("line.separator"));
            message.append(System.getProperty("line.separator"));
            message.append(System.getProperty("line.separator"));
			message.append("There is no way to achieve this.");
		}
		
		TextView results = findViewById(R.id.results);
		results.setText(message.toString());
	}

    public void initComboSeekBar(COLORS color, final TreeMap<Integer, Integer> landCombosMap) {
        int seekBarID;
        int untappedTextID;
        int tappedTextID;
        switch (color) {
            case red:
                seekBarID = R.id.redUntappedSeekBar;
                untappedTextID = R.id.redUntapped;
                tappedTextID = R.id.redTapped;
                break;
            default:
                seekBarID = R.id.redUntappedSeekBar;
                untappedTextID = R.id.redUntapped;
                tappedTextID = R.id.redTapped;
                break;
        }
        final SeekBar seekbar = (SeekBar) findViewById(seekBarID);
        final TextView untappedText = (TextView) findViewById(untappedTextID);
        final TextView tappedText = (TextView) findViewById(tappedTextID);
        final TreeMap<Integer, Integer> landCombosMapFinal = landCombosMap;
        untappedText.setText(landCombosMap.lastKey() + "");
        tappedText.setText(landCombosMap.get(landCombosMap.lastKey()) + "");
        seekbar.setProgress(landCombosMap.lastKey());
        seekbar.setMax(landCombosMap.lastKey() - landCombosMap.firstKey());
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                untappedText.setText((progress + landCombosMapFinal.firstKey()) + "");
                if (landCombosMapFinal.get((progress + landCombosMapFinal.firstKey())) != null) {
                    tappedText.setText(landCombosMapFinal.get((progress + landCombosMapFinal.firstKey())) + "");
                } else if (landCombosMapFinal.get((progress + landCombosMapFinal.firstKey()) - 1) != null) {
                    tappedText.setText(landCombosMapFinal.get((progress + landCombosMapFinal.firstKey()) - 1) + "");
                } else if (landCombosMapFinal.get((progress + landCombosMapFinal.firstKey()) - 2) != null) {
                    tappedText.setText(landCombosMapFinal.get((progress + landCombosMapFinal.firstKey()) - 2) + "");
                }else {
                    tappedText.setText(":(");
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

    public void hideSeekbar(COLORS color) {
        int seekBarID;
        int untappedTextID;
        int tappedTextID;
        switch (color) {
            case red:
                seekBarID = R.id.redUntappedSeekBar;
                untappedTextID = R.id.redUntapped;
                tappedTextID = R.id.redTapped;
                break;
            default:
                seekBarID = R.id.redUntappedSeekBar;
                untappedTextID = R.id.redUntapped;
                tappedTextID = R.id.redTapped;
                break;
        }
        final SeekBar seekbar = (SeekBar) findViewById(seekBarID);
        final TextView untappedText = (TextView) findViewById(untappedTextID);
        final TextView tappedText = (TextView) findViewById(tappedTextID);
        final TextView ifUseText = (TextView) findViewById(R.id.ifUseText);
        final TextView alsoNeedText = (TextView) findViewById(R.id.alsoNeedText);
        final TextView tappedLandText = (TextView) findViewById(R.id.tappedLandText);
        final TextView untappedLandText = (TextView) findViewById(R.id.untappedLandText);
        final ImageView tapSymbol = (ImageView) findViewById(R.id.tapSymbol);
        final ImageView untapSymbol = (ImageView) findViewById(R.id.untapSymbol);

        seekbar.setVisibility(View.INVISIBLE);
        untappedText.setVisibility(View.INVISIBLE);
        tappedText.setVisibility(View.INVISIBLE);
        ifUseText.setVisibility(View.INVISIBLE);
        alsoNeedText.setVisibility(View.INVISIBLE);
        tappedLandText.setVisibility(View.INVISIBLE);
        untappedLandText.setVisibility(View.INVISIBLE);
        tapSymbol.setVisibility(View.INVISIBLE);
        untapSymbol.setVisibility(View.INVISIBLE);
    }

}
