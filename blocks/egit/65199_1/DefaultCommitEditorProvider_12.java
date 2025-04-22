package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.jgit.util.IntList;

public class CommitMessageWrapper {

	static final int MAX_LINE_WIDTH = 72;

	protected static String wrapCommitMessage(String text) {
		int footerStart = CommonUtils.getFooterOffset(text);
		if (footerStart < 0) {
			return hardWrap(text);
		} else {
			String footer = text.substring(footerStart);
			text = hardWrap(text.substring(0, footerStart));
			return text + footer;
		}
	}

	protected static String hardWrap(String text) {
		int[] wrapOffsets = calculateWrapOffsets(text, MAX_LINE_WIDTH);
		if (wrapOffsets != null) {
			StringBuilder builder = new StringBuilder(
					text.length() + wrapOffsets.length);
			int prev = 0;
			for (int cur : wrapOffsets) {
				builder.append(text.substring(prev, cur));
				for (int j = cur; j > prev
						&& builder.charAt(builder.length() - 1) == ' '; j--)
					builder.deleteCharAt(builder.length() - 1);
				builder.append('\n');
				prev = cur;
			}
			builder.append(text.substring(prev));
			return builder.toString();
		}
		return text;
	}

	public static boolean shouldHardWrap() {
		return Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.COMMIT_DIALOG_HARD_WRAP_MESSAGE);
	}

	public static int[] calculateWrapOffsets(final String line,
			final int maxLineLength) {
		if (line.length() == 0)
			return null;

		IntList wrapOffsets = new IntList();
		int wordStart = 0;
		int lineStart = 0;
		boolean lastWasSpace = true;
		boolean onlySpaces = true;
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if (ch == ' ') {
				lastWasSpace = true;
			} else if (ch == '\n') {
				lineStart = i + 1;
				wordStart = i + 1;
				lastWasSpace = true;
				onlySpaces = true;
			} else { // a word character
				if (lastWasSpace) {
					lastWasSpace = false;
					if (!onlySpaces) { // don't break line with
						wordStart = i;
					}
				} else {
					onlySpaces = false;
				}
				if (i >= lineStart + maxLineLength) {
					if (wordStart != lineStart) { // don't break before a single
						wrapOffsets.add(wordStart);
						lineStart = wordStart;
						onlySpaces = true;
					}
				}
			}
		}

		int size = wrapOffsets.size();
		if (size == 0) {
			return null;
		} else {
			int[] result = new int[size];
			for (int i = 0; i < size; i++) {
				result[i] = wrapOffsets.get(i);
			}
			return result;
		}
	}
}
