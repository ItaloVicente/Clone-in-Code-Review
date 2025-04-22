
package org.eclipse.jgit.diff;

import junit.framework.TestCase;

public class MyersDiffTest extends TestCase {
	public void testAtEnd() {
		assertDiff("HELLO"
	}

	public void testAtStart() {
		assertDiff("Git"
	}

	public void testSimple() {
		assertDiff("HELLO WORLD"
			" -0
	}

	public void assertDiff(String a
		MyersDiff diff = new MyersDiff(toCharArray(a)
		assertEquals(edits
	}

	private static String toString(EditList list) {
		StringBuilder builder = new StringBuilder();
		for (Edit e : list)
			builder.append(" -" + e.beginA
					+ "
	}

	private static class CharArray implements Sequence {
		char[] array;
		public CharArray(String s) { array = s.toCharArray(); }
		public int size() { return array.length; }
		public boolean equals(int i
			CharArray o = (CharArray)other;
			return array[i] == o.array[j];
		}
	}
}
