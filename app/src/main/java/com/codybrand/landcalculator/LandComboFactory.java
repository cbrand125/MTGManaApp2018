package com.codybrand.landcalculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.commons.math3.distribution.HypergeometricDistribution;
import org.apache.commons.math3.util.Combinations;

public class LandComboFactory {

    public static ArrayList<LandSuggestion> findAllLandCombos(int deckSize, Double achieveChance, int usableSources, int byTurn, TreeMap<Integer, Integer> landCombosMap) {
        return findAllLandCombos(deckSize, achieveChance, usableSources, byTurn, landCombosMap, COLORS.red);
    }
	
	public static ArrayList<LandSuggestion> findAllLandCombos(int deckSize, Double achieveChance, int usableSources, int byTurn, TreeMap<Integer, Integer> landCombosMap, COLORS color) {
		ArrayList<LandSuggestion> landCombos = new ArrayList<LandSuggestion>();
		int tapped = 0;
		int untapped = 1;
		Double currentChance = new Double(0);
		int lastUntapped = 99999;

		while (tapped <= (deckSize/2) && untapped > 0) {
			untapped = 0;

            currentChance = calculate(deckSize, achieveChance, untapped, tapped, usableSources, byTurn, landCombos);
			while (currentChance < achieveChance) {
                untapped++;
                currentChance = calculate(deckSize, achieveChance, untapped, tapped, usableSources, byTurn, landCombos);
			}

            if ((currentChance >= achieveChance) && ((tapped + untapped) <= (deckSize/2)) && (untapped < lastUntapped)) {
			    LandSuggestion landSuggestion = new LandSuggestion(tapped, untapped);
			    switch (color) {
			        case red:
			            landSuggestion.red = true;
			            break;
                    case blue:
                        landSuggestion.blue = true;
                        break;
                    case black:
                        landSuggestion.black = true;
                        break;
                    case white:
                        landSuggestion.white = true;
                        break;
                    case green:
                        landSuggestion.green = true;
                        break;
                    case colorless:
                        landSuggestion.colorless = true;
                        break;
                    default:
                        break;
                }

                landCombos.add(landSuggestion);
			    landCombosMap.put(untapped, tapped);
            }

			tapped++;
			lastUntapped = untapped;
		}
		
		return landCombos;
	}

	private static Double calculate(int deckSize, Double achieveChance, int untappedSources, int tappedSources,
			int usableSources, int byTurn, ArrayList<LandSuggestion> landCombos) {
		
		// calculate untapped sources on turn
		HypergeometricDistribution untapped = new HypergeometricDistribution(
				deckSize, untappedSources, (7 + byTurn - 1));
		// calculate tapped sources before turn
		HypergeometricDistribution tapped = new HypergeometricDistribution(
				deckSize, tappedSources, (7 + byTurn - 2));

		Double fromUntapped = (double) 0;
		Double fromTapped = (double) 0;
		Double[] events = new Double[usableSources + 1];
		for (int i = 0; i <= usableSources; i++) {
			fromUntapped = untapped.upperCumulativeProbability(usableSources - i);
			fromTapped = tapped.upperCumulativeProbability(i);
			events[i] = fromTapped * fromUntapped;
		}

		if (usableSources == byTurn)
			events[usableSources] = (double) 0;
		
		Double totalChance = union(events);

		return totalChance;
	}

	private static Double union(Double[] A) {
		Double sum = (double) 0;
		Double product = (double) 1;
		int sign = 1;
		for (int i = 1; i <= A.length; i++) {
			Iterator<int[]> combos = new Combinations(A.length, i).iterator();
			while (combos.hasNext()) {
				int [] term = combos.next();
				for (int j = 0; j < i; j++) {
					product = product * (A[term[j]]);
				}
				sum = sum + (product * sign);
				product = (double) 1;
			}
			
			sign = sign * (-1);
		}
		return sum;
	}

}