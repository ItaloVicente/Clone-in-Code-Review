
package org.eclipse.jgit.diff;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class HistogramDiffTest extends AbstractDiffTestCase {
	@Override
	protected HistogramDiff algorithm() {
		HistogramDiff hd = new HistogramDiff();
		hd.setFallbackAlgorithm(null);
		return hd;
	}

	@Test
	public void testEdit_NoUniqueMiddleSide_FlipBlocks() {
		EditList r = diff(t("aRRSSz")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(5
	}

	@Test
	public void testEdit_NoUniqueMiddleSide_Insert2() {
		EditList r = diff(t("aRSz")
		assertEquals(1
		assertEquals(new Edit(2
	}

	@Test
	public void testEdit_NoUniqueMiddleSide_FlipAndExpand() {
		EditList r = diff(t("aRSz")
		assertEquals(2
		assertEquals(new Edit(1
		assertEquals(new Edit(3
	}

	@Test
	public void testEdit_LcsContainsUnique() {
		EditList r = diff(t("nqnjrnjsnm")
		assertEquals(new Edit(0
		assertEquals(new Edit(9
		assertEquals(new Edit(10
		assertEquals(3
	}

	@Test
	public void testExceedsChainLength_DuringScanOfA() {
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

	@Test
	public void testExceedsChainLength_DuringScanOfB() {
		HistogramDiff hd = new HistogramDiff();
		hd.setFallbackAlgorithm(null);
		hd.setMaxChainLength(1);

		EditList r = hd.diff(RawTextComparator.DEFAULT
		assertEquals(1
		assertEquals(new Edit(0
	}

	@Test
	public void testFallbackToMyersDiff() {
		HistogramDiff hd = new HistogramDiff();
		hd.setMaxChainLength(4);

		RawTextComparator cmp = RawTextComparator.DEFAULT;
		RawText ac = t("bbbbb");
		RawText bc = t("AbCbDbEFbZ");
		EditList r;

		hd.setFallbackAlgorithm(null);
		r = hd.diff(cmp
		assertEquals(1

		hd.setFallbackAlgorithm(MyersDiff.INSTANCE);
		r = hd.diff(cmp
		assertEquals(5
	}
}
