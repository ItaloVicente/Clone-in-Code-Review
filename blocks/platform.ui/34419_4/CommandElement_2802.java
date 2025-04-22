
package org.eclipse.ui.internal.quickaccess;

import java.util.ArrayList;
import java.util.List;

public class CamelUtil {

	public static String getCamelCase(String s) {
		StringBuffer result = new StringBuffer();
		if (s.length() > 0) {
			int index = 0;
			while (index != -1) {
				result.append(s.charAt(index));
				index = getNextCamelIndex(s, index + 1);
			}
		}
		return result.toString().toLowerCase();
	}

	public static int[][] getCamelCaseIndices(String s, int start, int length) {
		List result = new ArrayList();
		int index = 0;
		while (start > 0) {
			index = getNextCamelIndex(s, index + 1);
			start--;
		}
		while (length > 0) {
			result.add(new int[] { index, index });
			index = getNextCamelIndex(s, index + 1);
			length--;
		}
		return (int[][]) result.toArray(new int[result.size()][]);
	}

	public static int getNextCamelIndex(String s, int index) {
		char c;
		while (index < s.length()
				&& !(isSeparatorForCamelCase(c = s.charAt(index)))
				&& Character.isLowerCase(c)) {
			index++;
		}
		while (index < s.length() && isSeparatorForCamelCase(c = s.charAt(index))) {
			index++;
		}
		if (index >= s.length()) {
			index = -1;
		}
		return index;
	}

	public static boolean isSeparatorForCamelCase(char c) {
		return !Character.isLetterOrDigit(c);
	}

}
