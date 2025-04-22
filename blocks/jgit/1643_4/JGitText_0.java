
package org.eclipse.jgit.diff;

import org.eclipse.jgit.diff.DiffPerformanceTest.CharArray;
import org.eclipse.jgit.diff.DiffPerformanceTest.CharCmp;

public class HistogramDiffTest extends AbstractDiffTestCase {
	@Override
	protected HistogramDiff algorithm() {
		HistogramDiff hd = new HistogramDiff();
		hd.setFallbackAlgorithm(null);
		return hd;
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

	public void testExceedsChainLenght_DuringScanOfA() {
		HistogramDiff hd = new HistogramDiff();
		hd.setFallbackAlgorithm(null);
		hd.setMaxChainLength(3);

		SequenceComparator<RawText> cmp = new SequenceComparator<RawText>() {
			@Override
			public boolean equals(RawText a
				return RawTextComparator.DEFAULT.equals(a
			}

			@Override
			public int hash(RawText a
				return 1;
			}
		};

		EditList r = hd.diff(cmp
		assertEquals(1
		assertEquals(new Edit(0
	}

	public void testExceedsChainLenght_DuringScanOfB() {
		HistogramDiff hd = new HistogramDiff();
		hd.setFallbackAlgorithm(null);
		hd.setMaxChainLength(1);

		EditList r = hd.diff(RawTextComparator.DEFAULT
		assertEquals(1
		assertEquals(new Edit(0
	}

	public void testFallbackToMyersDiff() {
		HistogramDiff hd = new HistogramDiff();
		hd.setMaxChainLength(64);

		String a = DiffTestDataGenerator.generateSequence(40000
		String b = DiffTestDataGenerator.generateSequence(40000
		CharCmp cmp = new CharCmp();
		CharArray ac = new CharArray(a);
		CharArray bc = new CharArray(b);
		EditList r;

		hd.setFallbackAlgorithm(null);
		r = hd.diff(cmp
		assertEquals(70

		hd.setFallbackAlgorithm(MyersDiff.INSTANCE);
		r = hd.diff(cmp
		assertEquals(73

		EditList myersResult = MyersDiff.INSTANCE.diff(cmp
		assertFalse("Not same as Myers"
	}

	public void testPerformanceTestDeltaLength() {
		HistogramDiff hd = new HistogramDiff();
		hd.setFallbackAlgorithm(null);

		String a = DiffTestDataGenerator.generateSequence(40000
		String b = DiffTestDataGenerator.generateSequence(40000
		CharCmp cmp = new CharCmp();
		CharArray ac = new CharArray(a);
		CharArray bc = new CharArray(b);
		EditList r;

		hd.setMaxChainLength(64);
		r = hd.diff(cmp
		assertEquals(70

		hd.setMaxChainLength(176);
		r = hd.diff(cmp
		assertEquals(72
	}
}
