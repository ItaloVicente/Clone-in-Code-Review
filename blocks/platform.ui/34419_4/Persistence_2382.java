
package org.eclipse.ui.internal.activities;

public class PatternUtil {
	
	public static String quotePattern(String pattern) {
		final String START = "\\Q";  //$NON-NLS-1$
		final String STOP = "\\E"; //$NON-NLS-1$
		final int STOP_LENGTH = 2;  // STOP.length()
		
		int stopIndex = pattern.indexOf(STOP);
		if (stopIndex < 0) {
			return START + pattern + STOP;
		}
		
		String result = START;
		for (int position=0; ;) {
			stopIndex = pattern.indexOf(STOP, position);
			if (stopIndex >= 0) {
				result += pattern.substring(position, stopIndex + 2)
						+ "\\" + STOP + START; //$NON-NLS-1$
				position = stopIndex + STOP_LENGTH;
			} else {
				result += pattern.substring(position) + STOP;  
				break;
			}
		}
		
		return result;
	}	
}
