
package org.eclipse.jgit.diff;

import org.eclipse.jgit.diff.DiffPerformanceTest.CharArray;
import org.eclipse.jgit.diff.DiffPerformanceTest.CharCmp;

public class HistogramDiffTest extends AbstractDiffTestCase {
	@Override
	protected DiffAlgorithm algorithm() {
		return HistogramDiff.INSTANCE;
	}

	public void testEdit_NoUniqueMiddleSide_FlipBlocks() {
		EditList r = diff(t("aRRSSz")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(5
	}

	public void testEdit_NoUniqueMiddleSide_Insert2() {
		EditList r = diff(t("aRSz")
		assertEquals(1
		assertEquals(new Edit(2
	}

	public void testEdit_NoUniqueMiddleSide_FlipAndExpand() {
		EditList r = diff(t("aRSz")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(3
	}

	public void testPerformanceTestDeltaLength() {
		String a = DiffTestDataGenerator.generateSequence(40000
		String b = DiffTestDataGenerator.generateSequence(40000
		CharArray ac = new CharArray(a);
		CharArray bc = new CharArray(b);
		EditList r = algorithm().diff(new CharCmp()
		assertEquals(71
	}
}
