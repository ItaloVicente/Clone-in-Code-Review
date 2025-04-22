
package org.eclipse.jgit.diff;

import static org.eclipse.jgit.diff.PatienceDiff.diff;

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

	public void testBogus1() {
		EditList r = diff(t("RbC")
		assertEquals(2
		assertEquals(new Edit(0
		assertEquals(new Edit(2
	}

	public void testBogus2() {
		EditList r = diff(t("aRbCd")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(3
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
}
