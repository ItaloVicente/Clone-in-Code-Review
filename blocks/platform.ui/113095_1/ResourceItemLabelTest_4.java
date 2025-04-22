
		Position[] withCamelCase = { new Position(4, 3), new Position(8, 1) };
		compareStyleRanges(withCamelCase, getStyleRanges(".TxT", "test.TxxT"));

		Position[] withPattern = { new Position(4, 2), new Position(8, 1) };
		compareStyleRanges(withPattern, getStyleRanges(".t*t", "test.txxt"));
	}

	public void testBug528301() throws Exception {
		Position[] withSameLettersBeforeCamel = { new Position(0, 1), new Position(3, 1), new Position(5, 1) };
		compareStyleRanges(withSameLettersBeforeCamel, getStyleRanges("ABC", "AbcBzCz.txt"));
