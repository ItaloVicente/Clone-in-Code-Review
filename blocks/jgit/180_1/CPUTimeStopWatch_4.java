
package org.eclipse.jgit.merge;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.Constants;

public class MergeAlgorithmTest extends TestCase {
	MergeFormatter fmt=new MergeFormatter();

	private static final String A = "aaa\n";
	private static final String B = "bbbbb\nbb\nbbb\n";
	private static final String C = "c\n";
	private static final String D = "dd\n";
	private static final String E = "ee\n";
	private static final String F = "fff\nff\n";
	private static final String G = "gg\n";
	private static final String H = "h\nhhh\nhh\n";
	private static final String I = "iiii\n";
	private static final String J = "jj\n";
	private static final String Z = "zzz\n";
	private static final String Y = "y\n";

	private static final String XXX_0 = "<<<<<<< O\n";
	private static final String XXX_1 = "=======\n";
	private static final String XXX_2 = ">>>>>>> T\n";

	String base=A+B+C+D+E+F+G+H+I+J;

	String replace_C_by_Z=A+B+Z+D+E+F+G+H+I+J;
	String replace_A_by_Y=Y+B+C+D+E+F+G+H+I+J;
	String replace_A_by_Z=Z+B+C+D+E+F+G+H+I+J;
	String replace_J_by_Y=A+B+C+D+E+F+G+H+I+Y;
	String replace_J_by_Z=A+B+C+D+E+F+G+H+I+Z;
	String replace_BC_by_ZZ=A+Z+Z+D+E+F+G+H+I+J;
	String replace_BCD_by_ZZZ=A+Z+Z+Z+E+F+G+H+I+J;
	String replace_BD_by_ZZ=A+Z+C+Z+E+F+G+H+I+J;
	String replace_BCDEGI_by_ZZZZZZ=A+Z+Z+Z+Z+F+Z+H+Z+J;
	String replace_CEFGHJ_by_YYYYYY=A+B+Y+D+Y+Y+Y+Y+I+Y;
	String replace_BDE_by_ZZY=A+Z+C+Z+Y+F+G+H+I+J;

	public void testTwoConflictingModifications() throws IOException {
		assertEquals(A + XXX_0 + B + Z + XXX_1 + Z + Z + XXX_2 + D + E + F + G
				+ H + I + J
				merge(base
	}

	public void testOneAgainstTwoConflictingModifications() throws IOException {
		assertEquals(A + XXX_0 + Z + Z + Z + XXX_1 + Z + C + Z + XXX_2 + E + F
				+ G + H + I + J
				merge(base
	}

	public void testNoAgainstOneModification() throws IOException {
		assertEquals(replace_BD_by_ZZ.toString()
				merge(base
	}

	public void testTwoNonConflictingModifications() throws IOException {
		assertEquals(Y + B + Z + D + E + F + G + H + I + J
			merge(base
	}

	public void testTwoComplicatedModifications() throws IOException {
		assertEquals(A + XXX_0 + Z + Z + Z + Z + F + Z + H + XXX_1 + B + Y + D
				+ Y + Y + Y + Y + XXX_2 + Z + Y
				merge(base
						replace_BCDEGI_by_ZZZZZZ
						replace_CEFGHJ_by_YYYYYY));
	}

	public void testConflictAtStart() throws IOException {
		assertEquals(XXX_0 + Z + XXX_1 + Y + XXX_2 + B + C + D + E + F + G + H
				+ I + J
	}

	public void testConflictAtEnd() throws IOException {
		assertEquals(A+B+C+D+E+F+G+H+I+XXX_0+Z+XXX_1+Y+XXX_2
	}

	private String merge(String commonBase
		MergeResult r=MergeAlgorithm.merge(new RawText(Constants.encode(commonBase))
		ByteArrayOutputStream bo=new ByteArrayOutputStream(50);
		fmt.formatMerge(bo
		return new String(bo.toByteArray()
	}
}
