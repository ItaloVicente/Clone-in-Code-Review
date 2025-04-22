	/**
	 * Test situations where (at least) one input value is the empty text
	 *
	 * @throws IOException
	 */
	@Test
	public void testEmptyTexts() throws IOException {
		assertEquals(t("<AB=>"), merge("A", "AB", ""));
		assertEquals(t("<=AB>"), merge("A", "", "AB"));

		assertEquals(t(""), merge("AB", "AB", ""));
		assertEquals(t(""), merge("AB", "", "AB"));

		assertEquals(t(""), merge("AB", "", ""));
	}

	private String merge(String commonBase, String ours, String theirs) throws IOException {
		MergeResult r = new MergeAlgorithm().merge(RawTextComparator.DEFAULT,
				T(commonBase), T(ours), T(theirs));
		ByteArrayOutputStream bo=new ByteArrayOutputStream(50);
		fmt.formatMerge(bo, r, "B", "O", "T", Constants.CHARACTER_ENCODING);
		return new String(bo.toByteArray(), Constants.CHARACTER_ENCODING);
	}

	public String t(String text) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (c) {
			case '<':
				r.append("<<<<<<< O\n");
				break;
			case '=':
				r.append("=======\n");
				break;
			case '>':
				r.append(">>>>>>> T\n");
				break;
			default:
				r.append(c);
				if (newlineAtEnd || i < text.length() - 1)
					r.append('\n');
			}
		}
		return r.toString();
	}

	public RawText T(String text) {
		return new RawText(Constants.encode(t(text)));
	}
}
