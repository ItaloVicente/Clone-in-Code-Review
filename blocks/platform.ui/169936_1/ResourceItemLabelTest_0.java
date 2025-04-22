	@Test
	public void testBug566858_leadingWhitespace() throws Exception {
		Position[] pos = new Position[] { new Position(0, 5) };
		compareStyleRanges(pos, getStyleRanges(" test", " test.txt"), " test.txt", "");

		pos = new Position[] { new Position(1, 4) };
		compareStyleRanges(pos, getStyleRanges("?test", " test.txt"), " test.txt", "");

		pos = new Position[] { new Position(5, 4) };
		compareStyleRanges(pos, getStyleRanges("*.txt", " test.txt"), " test.txt", "");
	}

