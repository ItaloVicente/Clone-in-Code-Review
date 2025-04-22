	public void testBug529451() throws Exception {
		Position[] basic = { new Position(4, 1) };
		compareStyleRanges(basic, getStyleRanges("*$", "test$.txt"));

		Position[] multiple = { new Position(0, 3), new Position(7, 9), new Position(17, 1) };
		compareStyleRanges(multiple, getStyleRanges("^${*}[])(+|><?-", "^${skip}[])(+|><s-"));
	}

