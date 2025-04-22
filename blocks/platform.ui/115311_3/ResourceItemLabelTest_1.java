	public void testDisableAutoPrefixMatching() throws Exception {
		Position[] questionMark = { new Position(0, 1), new Position(4, 4) };
		compareStyleRanges(questionMark, getStyleRanges("M*file<", "Makefile"));

		Position[] star = { new Position(0, 1), new Position(4, 4) };
		compareStyleRanges(star, getStyleRanges("M*file ", "MockFile"));

		Position[] both = { new Position(0, 3), new Position(6, 1) };
		compareStyleRanges(both, getStyleRanges("CreS<", "CreateStuff.java"));
	}

