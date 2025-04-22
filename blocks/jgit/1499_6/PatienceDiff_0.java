
package org.eclipse.jgit.diff;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

public class PatienceDiffTest extends TestCase {
	public void testEmptyInputs() {
		EditList r = diff(t("")
		assertTrue("is empty"
	}

	public void testCreateFile() {
		EditList r = diff(t("")
		assertEquals(1
		assertEquals(new Edit(0
	}

	public void testDeleteFile() {
		EditList r = diff(t("AB")
		assertEquals(1
		assertEquals(new Edit(0
	}

	public void testDegenerate_InsertMiddle() {
		EditList r = diff(t("ac")
		assertEquals(1
		assertEquals(new Edit(1
	}

	public void testDegenerate_DeleteMiddle() {
		EditList r = diff(t("aBc")
		assertEquals(1
		assertEquals(new Edit(1
	}

	public void testDegenerate_ReplaceMiddle() {
		EditList r = diff(t("bCd")
		assertEquals(1
		assertEquals(new Edit(1
	}

	public void testDegenerate_InsertsIntoMidPosition() {
		EditList r = diff(t("aaaa")
		assertEquals(1
		assertEquals(new Edit(2
	}

	public void testDegenerate_InsertStart() {
		EditList r = diff(t("bc")
		assertEquals(1
		assertEquals(new Edit(0
	}

	public void testDegenerate_DeleteStart() {
		EditList r = diff(t("Abc")
		assertEquals(1
		assertEquals(new Edit(0
	}

	public void testDegenerate_InsertEnd() {
		EditList r = diff(t("bc")
		assertEquals(1
		assertEquals(new Edit(2
	}

	public void testDegenerate_DeleteEnd() {
		EditList r = diff(t("bcD")
		assertEquals(1
		assertEquals(new Edit(2
	}

	public void testEdit_ReplaceCommonDelete() {
		EditList r = diff(t("RbC")
		assertEquals(2
		assertEquals(new Edit(0
		assertEquals(new Edit(2
	}

	public void testEdit_CommonReplaceCommonDeleteCommon() {
		EditList r = diff(t("aRbCd")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(3
	}

	public void testEdit_MoveBlock() {
		EditList r = diff(t("aYYbcdz")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(6
	}

	public void testEdit_InvertBlocks() {
		EditList r = diff(t("aYYbcdXXz")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(6
	}

	public void testEdit_NoUniqueMiddleSideA() {
		EditList r = diff(t("aRRSSz")
		assertEquals(1
		assertEquals(new Edit(1
	}

	public void testEdit_NoUniqueMiddleSideB() {
		EditList r = diff(t("aRSz")
		assertEquals(1
		assertEquals(new Edit(1
	}

	public void testEdit_UniqueCommonLargerThanMatchPoint() {
		EditList r = diff(t("AbdeZ")
		assertEquals(2
		assertEquals(new Edit(0
		assertEquals(new Edit(4
	}

	public void testEdit_CommonGrowsPrefixAndSuffix() {
		EditList r = diff(t("AaabccZ")
		assertEquals(2
		assertEquals(new Edit(0
		assertEquals(new Edit(6
	}

	public void testPerformanceTestDeltaLength() {
		String a = DiffTestDataGenerator.generateSequence(40000
		String b = DiffTestDataGenerator.generateSequence(40000
		CharArray ac = new CharArray(a);
		CharArray bc = new CharArray(b);
		EditList r = PatienceDiff.diff(new CharCmp()
		assertEquals(25
	}

	private static EditList diff(RawText a
		return PatienceDiff.diff(RawTextComparator.DEFAULT
	}

	private static RawText t(String text) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			r.append(text.charAt(i));
			r.append('\n');
		}
		try {
			return new RawText(r.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static class CharArray extends Sequence {
		final char[] array;

		public CharArray(String s) {
			array = s.toCharArray();
		}

		@Override
		public int size() {
			return array.length;
		}
	}

	private static class CharCmp extends SequenceComparator<CharArray> {
		@Override
		public boolean equals(CharArray a
			return a.array[ai] == b.array[bi];
		}

		@Override
		public int hash(CharArray seq
			return seq.array[ptr];
		}
	}
}
