
		Position[] withCamelCase = { new Position(4, 3), new Position(8, 1) };
		compareStyleRanges(withCamelCase, getStyleRanges(".TxT", "test.TxxT"));

		Position[] withPattern = { new Position(4, 2), new Position(8, 1) };
		compareStyleRanges(withPattern, getStyleRanges(".t*t", "test.txxt"));

		Position[] withDigits = { new Position(4, 2), new Position(8, 1) };
		compareStyleRanges(withDigits, getStyleRanges(".3*3", "test.3xx3"));
	}

	public void testBug528301() throws Exception {
		Position[] withSameLettersBeforeCamel = { new Position(0, 1), new Position(3, 1), new Position(5, 1) };
		compareStyleRanges(withSameLettersBeforeCamel, getStyleRanges("ABC", "AbcBzCz.txt"));

		Position[] withDigits = { new Position(0, 1), new Position(4, 1), new Position(6, 1), new Position(8, 1) };
		compareStyleRanges(withDigits, getStyleRanges("AB5C", "Ab5cBz5zCz.txt"));
