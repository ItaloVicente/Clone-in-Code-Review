		assertEquals(73, r.size());

		EditList myersResult = MyersDiff.INSTANCE.diff(cmp, ac, bc);
		assertFalse("Not same as Myers", myersResult.equals(r));
	}

	public void testPerformanceTestDeltaLength() {
		HistogramDiff hd = new HistogramDiff();
		hd.setFallbackAlgorithm(null);

		String a = DiffTestDataGenerator.generateSequence(40000, 971, 3);
		String b = DiffTestDataGenerator.generateSequence(40000, 1621, 5);
		CharCmp cmp = new CharCmp();
		CharArray ac = new CharArray(a);
		CharArray bc = new CharArray(b);
		EditList r;

		hd.setMaxChainLength(64);
		r = hd.diff(cmp, ac, bc);
		assertEquals(70, r.size());

		hd.setMaxChainLength(176);
		r = hd.diff(cmp, ac, bc);
		assertEquals(72, r.size());
