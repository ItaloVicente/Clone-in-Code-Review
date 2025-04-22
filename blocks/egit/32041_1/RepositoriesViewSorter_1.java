package org.eclipse.egit.core.internal;

import static java.lang.Character.isDigit;

import java.util.Comparator;

public class NaturalStringComparator implements Comparator<String> {

	public static NaturalStringComparator INSTANCE = new NaturalStringComparator();

	private NaturalStringComparator() {
	}

	public int compare(String str1, String str2) {
		int pos1 = 0;
		int pos2 = 0;

		while (pos1 < str1.length() && pos2 < str2.length()) {
			boolean digit1 = isDigit(str1.charAt(pos1));
			boolean digit2 = isDigit(str2.charAt(pos2));

			if (digit1 && digit2) {
				int i1 = pos1;
				while (i1 < str1.length() && isDigit(str1.charAt(i1)))
					i1++;
				Long n1 = Long.valueOf(str1.substring(pos1, i1));
				pos1 = i1;

				int i2 = pos2;
				while (i2 < str2.length() && isDigit(str2.charAt(i2)))
					i2++;
				Long n2 = Long.valueOf(str2.substring(pos2, i2));
				pos2 = i2;

				int res = n1.compareTo(n2);
				if (res != 0)
					return res;
			} else if (digit1 != digit2) {
				return str1.substring(pos1).compareTo(str2.substring(pos2));
			} else {
				int i1 = pos1;
				while (i1 < str1.length() && !isDigit(str1.charAt(i1))) {
					i1++;
				}
				String t1 = str1.substring(pos1, i1);
				pos1 = i1;

				int i2 = pos2;
				while (i2 < str2.length() && !isDigit(str2.charAt(i2))) {
					i2++;
				}
				String t2 = str2.substring(pos2, i2);
				pos2 = i2;

				int res = t1.compareTo(t2);
				if (res != 0)
					return res;
			}
		}

		return 0;
	}
}
