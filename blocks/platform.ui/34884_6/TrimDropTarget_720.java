
package org.eclipse.ui.internal;

public class TrimDragPreferences {


	private static int thresholdPref = 50;
	
	private static boolean raggedTrim = true;

	public static int getThreshold() {
		return thresholdPref;
	}

	public static void setThreshold(int threshold) {
		thresholdPref = threshold;
	}

	public static boolean showRaggedTrim() {
		return raggedTrim;
	}

	public static void setRaggedTrim(boolean raggedTrim) {
		TrimDragPreferences.raggedTrim = raggedTrim;
	}
}
