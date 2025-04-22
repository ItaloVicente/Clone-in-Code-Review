
package org.eclipse.jgit.merge;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.jgit.diff.RawText;

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
	String mod_c_z=A+B+Z+D+E+F+G+H+I+J;
	String mod_a_y=Y+B+C+D+E+F+G+H+I+J;
	String mod_a_z=Z+B+C+D+E+F+G+H+I+J;
	String mod_j_y=A+B+C+D+E+F+G+H+I+Y;
	String mod_j_z=A+B+C+D+E+F+G+H+I+Z;
	String mod_bc_zz=A+Z+Z+D+E+F+G+H+I+J;
	String mod_bcd_zzz=A+Z+Z+Z+E+F+G+H+I+J;
	String mod_bd_zz=A+Z+C+Z+E+F+G+H+I+J;
	String mod_bcdegi_zzzzzz=A+Z+Z+Z+Z+F+Z+H+Z+J;
	String mod_cefghj_yyyyyy=A+B+Y+D+Y+Y+Y+Y+I+Y;
	String mod_bde_zzy=A+Z+C+Z+Y+F+G+H+I+J;

	private RawText s2r(String txt) {
		return new RawText(txt.getBytes());
	}

	public void testTwoConflictingModifications() throws IOException {
		assertEquals(A+XXX_0+B+Z+XXX_1+Z+Z+XXX_2+D+E+F+G+H+I+J
	}

	public void testOneAgainstTwoConflictingModifications() throws IOException {
		assertEquals(A+XXX_0+Z+Z+Z+XXX_1+Z+C+Z+XXX_2+E+F+G+H+I+J
	}

	public void testNoAgainstOneModification() throws IOException {
		assertEquals(mod_bd_zz.toString()
	}

	public void testTwoNonConflictingModifications() throws IOException {
		assertEquals(Y+B+Z+D+E+F+G+H+I+J
	}

	public void testTwoComplicatedModifications() throws IOException {
		assertEquals(A+XXX_0+Z+Z+Z+Z+F+Z+H+XXX_1+B+Y+D+Y+Y+Y+Y+XXX_2+Z+Y
	}

	public void testConflictAtStart() throws IOException {
		assertEquals(XXX_0+Z+XXX_1+Y+XXX_2+B+C+D+E+F+G+H+I+J
	}

	public void testConflictAtEnd() throws IOException {
		assertEquals(A+B+C+D+E+F+G+H+I+XXX_0+Z+XXX_1+Y+XXX_2
	}

	String merge(String base
		MergeResult r=MergeAlgorithm.merge(s2r(base)
		ByteArrayOutputStream bo=new ByteArrayOutputStream(50);
		fmt.formatMerge(bo
		return new String(bo.toByteArray());
	}
}
