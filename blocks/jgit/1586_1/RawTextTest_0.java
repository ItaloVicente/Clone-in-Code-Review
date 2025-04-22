
	public void testComparatorReduceCommonStartEnd()
			throws UnsupportedEncodingException {
		final RawTextComparator c = RawTextComparator.DEFAULT;
		Edit e;

		e = c.reduceCommonStartEnd(t("")
		assertEquals(new Edit(0

		e = c.reduceCommonStartEnd(t("a")
		assertEquals(new Edit(0

		e = c.reduceCommonStartEnd(t("a")
		assertEquals(new Edit(1

		e = c.reduceCommonStartEnd(t("axB")
		assertEquals(new Edit(2

		e = c.reduceCommonStartEnd(t("Bxy")
		assertEquals(new Edit(0

		e = c.reduceCommonStartEnd(t("bc")
		assertEquals(new Edit(0

		e = new Edit(0
		e = c.reduceCommonStartEnd(t("abQxy")
		assertEquals(new Edit(2

		RawText a = new RawText("p\na b\nQ\nc d\n".getBytes("UTF-8"));
		RawText b = new RawText("p\na  b \nR\n c  d \n".getBytes("UTF-8"));
		e = new Edit(0
		e = RawTextComparator.WS_IGNORE_ALL.reduceCommonStartEnd(a
		assertEquals(new Edit(2
	}

	private static RawText t(String text) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			r.append(text.charAt(i));
			r.append('\n');
		}
		try {
			return new RawText(r.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
