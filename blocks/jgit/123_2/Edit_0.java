
package org.eclipse.jgit.merge;

import org.eclipse.jgit.diff.RawText;

import junit.framework.TestCase;

public class MergeAlgorithmTest extends TestCase {
	String base="aaa\nbbbbb\nc\ndd\n";
	String oneSingleLineMod="aaa\nbbbbb\n00\ndd\n";
	String oneOtherSingleLineMod="11\nbbbbb\nc\ndd\n";
	String oneTwoLineMod="aaa\n00\n00\ndd\n";
	String oneThreeLineMod="aaa\n00\n00\n00\n";
	String twoSingleLineMods="aaa\n00\nc\n00\n";

	private RawText s2r(String txt) {
		return new RawText(txt.getBytes());
	}

	public void testTwoConflictingModifications() {
		assertEquals("aaa\n<<<<<<<\nbbbbb\n00\n=======\n00\n00\n>>>>>>>\ndd\n"
	}

	public void testOneAgainstTwoConflictingModifications() {
		assertEquals("aaa\n<<<<<<<\n00\n00\n00\n=======\n00\nc\n00\n>>>>>>>\n"
	}

	public void testNoAgainstOneModification() {
		assertEquals(twoSingleLineMods.toString()
	}

	public void testTwoNonConflictingModifications() {
		assertEquals("11\nbbbbb\n00\ndd\n"
	}

}
