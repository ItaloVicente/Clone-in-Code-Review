
		pd.setFallbackAlgorithm(HistogramDiff.INSTANCE);
		r = pd.diff(new CharCmp()
		assertEquals(71

		pd.setFallbackAlgorithm(MyersDiff.INSTANCE);
		r = pd.diff(new CharCmp()
		assertEquals(73
