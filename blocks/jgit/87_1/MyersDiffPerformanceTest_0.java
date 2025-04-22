
package org.eclipse.jgit.diff;

public class DiffTestDataGenerator {
	public static String generateSequence(int len) {
		return (generateSequence(len
	}

	public static String generateSequence(int len
			int skipLength) {
		StringBuilder text = new StringBuilder();
		int skipStart = skipPeriod - skipLength;
		int skippedChars = 0;
		for (int i = 0; i - skippedChars < len; ++i) {
			if (skipPeriod == 0 || i % skipPeriod < skipStart) {
				text.append((char) (32 + i % 95));
			} else {
				skippedChars++;
			}
		}
		return (text.toString());
	}
}
