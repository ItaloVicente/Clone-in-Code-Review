
package org.eclipse.jgit.merge;

import org.eclipse.jgit.diff.RawText;

import junit.framework.TestCase;

public class MergeAlgorithmTest extends TestCase {
	RawText textA=new RawText("a\nb\nc\nd\n".getBytes());
	RawText textB=new RawText("a\nb\n0\nd\n".getBytes());
	RawText textC=new RawText("a\n0\n0\nd\n".getBytes());
	String expectedABC="a\n<<<<<<<\nb\n0\n=======\n0\n0\n>>>>>>>\nd\n";
	public void testMergeAlgorithm() {
		assertEquals(expectedABC
	}

}
