	@Test
	public void testComparatorReduceCommonStartButLastLineNoEol()
			throws UnsupportedEncodingException {
		RawText a;
		RawText b;
		Edit e;
		a = new RawText("start".getBytes("UTF-8"));
		b = new RawText("start of line".getBytes("UTF-8"));
		e = new Edit(0
		e = RawTextComparator.DEFAULT.reduceCommonStartEnd(a
		assertEquals(new Edit(0
	}

	@Test
	public void testComparatorReduceCommonStartButLastLineNoEol_2()
			throws UnsupportedEncodingException {
		RawText a;
		RawText b;
		Edit e;
		a = new RawText("start".getBytes("UTF-8"));
		b = new RawText("start of\nlastline".getBytes("UTF-8"));
		e = new Edit(0
		e = RawTextComparator.DEFAULT.reduceCommonStartEnd(a
		assertEquals(new Edit(0
	}

