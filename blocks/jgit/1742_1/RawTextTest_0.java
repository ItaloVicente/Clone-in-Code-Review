	public void testComparatorReduceCommonStartEnd_EmptyLine()
			throws UnsupportedEncodingException {
		RawText a;
		RawText b;
		Edit e;

		a = new RawText("R\n y\n".getBytes("UTF-8"));
		b = new RawText("S\n\n y\n".getBytes("UTF-8"));
		e = new Edit(0
		e = RawTextComparator.DEFAULT.reduceCommonStartEnd(a
		assertEquals(new Edit(0

		a = new RawText("S\n\n y\n".getBytes("UTF-8"));
		b = new RawText("R\n y\n".getBytes("UTF-8"));
		e = new Edit(0
		e = RawTextComparator.DEFAULT.reduceCommonStartEnd(a
		assertEquals(new Edit(0
	}

