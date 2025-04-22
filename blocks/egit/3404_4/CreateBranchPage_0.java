package org.eclipse.egit.ui.internal;

import java.util.Comparator;
import java.util.regex.Pattern;

public class CommonUtils {

	private CommonUtils() {
	}

	private static final Pattern NUMBERS = Pattern.compile("[\\d]*"); //$NON-NLS-1$

	private static final Pattern CHARS = Pattern.compile("[^0-9]*"); //$NON-NLS-1$

	public static final Comparator<String> STRING_ASCENDING_COMPARATOR = new Comparator<String>() {
		public int compare(String o1, String o2) {
			String o1Chars = NUMBERS.matcher(o1).replaceAll(""); //$NON-NLS-1$
			String o2Chars = NUMBERS.matcher(o2).replaceAll(""); //$NON-NLS-1$
			int charCompare = o1Chars.compareTo(o2Chars);

			if (charCompare == 0) {
				String o1Numbers = CHARS.matcher(o1).replaceAll(""); //$NON-NLS-1$
				String o2Numbers = CHARS.matcher(o2).replaceAll(""); //$NON-NLS-1$
				if (o1Numbers.length() == 0)
					o1Numbers = "0"; //$NON-NLS-1$
				if (o2Numbers.length() == 0)
					o2Numbers = "0"; //$NON-NLS-1$

				return Integer.parseInt(o2Numbers)
						- Integer.parseInt(o1Numbers);
			}

			return charCompare;
		}
	};

}
