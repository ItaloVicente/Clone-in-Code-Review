import junit.framework.TestCase;

public class MyersDiffTest extends TestCase {
	public void testAtEnd() {
		assertDiff("HELLO", "HELL", " -4,1 +4,0");
	}

	public void testAtStart() {
		assertDiff("Git", "JGit", " -0,0 +0,1");
	}

	public void testSimple() {
		assertDiff("HELLO WORLD", "LOW",
			" -0,3 +0,0 -5,1 +2,0 -7,4 +3,0");
	}

	public void assertDiff(String a, String b, String edits) {
		EditList editList = MyersDiff.INSTANCE.diff(new CharCmp(),
				toCharArray(a), toCharArray(b));
		assertEquals(edits, toString(editList));
	}

	private static String toString(EditList list) {
		StringBuilder builder = new StringBuilder();
		for (Edit e : list)
			builder.append(" -" + e.beginA
					+ ", + (e.endA - e.beginA)
-				+  +" + e.beginB + ", + (e.endB - e.beginB));
-		return builder.toString();
-	}
-
-	private static CharArray toCharArray(String s) {
-		return new CharArray(s);
-	}
-
-	protected static String toString(CharArray a, int begin, int end) {
-		return new String(a.array, begin, end - begin);
-	}
-
-	protected static String toString(CharArray a, CharArray b,
-			int x, int k) {
-		return (" + x + ", + (k + x)
-			+ (x < 0 ? '<' :
-					(x >= a.array.length ?
-					 '>' : a.array[x]))
-			+ (k + x < 0 ? '<' :
-					(k + x >= b.array.length ?
-					 '>' : b.array[k + x]))
-			+ )";
	}

	private static class CharArray extends Sequence {
		char[] array;
		public CharArray(String s) { array = s.toCharArray(); }
		public int size() { return array.length; }
	}

	private static class CharCmp extends SequenceComparator<CharArray> {
		@Override
		public boolean equals(CharArray a, int ai, CharArray b, int bi) {
			return a.array[ai] == b.array[bi];
		}

		@Override
		public int hash(CharArray seq, int ptr) {
			return seq.array[ptr];
		}
