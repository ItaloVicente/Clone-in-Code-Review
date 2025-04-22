
package org.eclipse.jgit.diff;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public abstract class AbstractDiffTestCase {
	@Test
	public void testEmptyInputs() {
		EditList r = diff(t("")
		assertTrue("is empty"
	}

	@Test
	public void testCreateFile() {
		EditList r = diff(t("")
		assertEquals(1
		assertEquals(new Edit(0
	}

	@Test
	public void testDeleteFile() {
		EditList r = diff(t("AB")
		assertEquals(1
		assertEquals(new Edit(0
	}

	@Test
	public void testDegenerate_InsertMiddle() {
		EditList r = diff(t("ac")
		assertEquals(1
		assertEquals(new Edit(1
	}

	@Test
	public void testDegenerate_DeleteMiddle() {
		EditList r = diff(t("aBc")
		assertEquals(1
		assertEquals(new Edit(1
	}

	@Test
	public void testDegenerate_ReplaceMiddle() {
		EditList r = diff(t("bCd")
		assertEquals(1
		assertEquals(new Edit(1
	}

	@Test
	public void testDegenerate_InsertsIntoMidPosition() {
		EditList r = diff(t("aaaa")
		assertEquals(1
		assertEquals(new Edit(2
	}

	@Test
	public void testDegenerate_InsertStart() {
		EditList r = diff(t("bc")
		assertEquals(1
		assertEquals(new Edit(0
	}

	@Test
	public void testDegenerate_DeleteStart() {
		EditList r = diff(t("Abc")
		assertEquals(1
		assertEquals(new Edit(0
	}

	@Test
	public void testDegenerate_InsertEnd() {
		EditList r = diff(t("bc")
		assertEquals(1
		assertEquals(new Edit(2
	}

	@Test
	public void testDegenerate_DeleteEnd() {
		EditList r = diff(t("bcD")
		assertEquals(1
		assertEquals(new Edit(2
	}

	@Test
	public void testEdit_ReplaceCommonDelete() {
		EditList r = diff(t("RbC")
		assertEquals(2
		assertEquals(new Edit(0
		assertEquals(new Edit(2
	}

	@Test
	public void testEdit_CommonReplaceCommonDeleteCommon() {
		EditList r = diff(t("aRbCd")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(3
	}

	@Test
	public void testEdit_MoveBlock() {
		EditList r = diff(t("aYYbcdz")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(6
	}

	@Test
	public void testEdit_InvertBlocks() {
		EditList r = diff(t("aYYbcdXXz")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(6
	}

	@Test
	public void testEdit_UniqueCommonLargerThanMatchPoint() {
		EditList r = diff(t("AbdeZ")
		assertEquals(2
		assertEquals(new Edit(0
		assertEquals(new Edit(4
	}

	@Test
	public void testEdit_CommonGrowsPrefixAndSuffix() {
		EditList r = diff(t("AaabccZ")
		assertEquals(2
		assertEquals(new Edit(0
		assertEquals(new Edit(6
	}

	@Test
	public void testEdit_DuplicateAButCommonUniqueInB() {
		EditList r = diff(t("AbbcR")
		assertEquals(2
		assertEquals(new Edit(0
		assertEquals(new Edit(4
	}

	@Test
	public void testEdit_InsertNearCommonTail() {
		EditList r = diff(t("aq}nb")
		assertEquals(new Edit(1
		assertEquals(new Edit(4
		assertEquals(2
	}

	@Test
	public void testEdit_DeleteNearCommonTail() {
		EditList r = diff(t("aCq}nD}nb")
		assertEquals(new Edit(1
		assertEquals(new Edit(5
		assertEquals(2
	}

	@Test
	public void testEdit_DeleteNearCommonCenter() {
		EditList r = diff(t("abcd123123uvwxpq")
		assertEquals(new Edit(1
		assertEquals(new Edit(7
		assertEquals(new Edit(14
		assertEquals(3
	}

	@Test
	public void testEdit_InsertNearCommonCenter() {
		EditList r = diff(t("aBcd123uvwxPq")
		assertEquals(new Edit(1
		assertEquals(new Edit(7
		assertEquals(new Edit(11
		assertEquals(3
	}

	@Test
	public void testEdit_LinuxBug() {
		EditList r = diff(t("a{bcdE}z")
		assertEquals(new Edit(2
		assertEquals(new Edit(6
		assertEquals(2
	}

	public EditList diff(RawText a
		return algorithm().diff(RawTextComparator.DEFAULT
	}

	protected abstract DiffAlgorithm algorithm();

	public static RawText t(String text) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			r.append(text.charAt(i));
			r.append('\n');
		}
		return new RawText(r.toString().getBytes(UTF_8));
	}
}
