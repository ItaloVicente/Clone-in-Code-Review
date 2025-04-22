
		Position[] withDigits = { new Position(0, 2), new Position(4, 2) };
		compareStyleRanges(withDigits, getStyleRanges("Th3T", "This3Test.txt"));

		Position[] skippingDigit = { new Position(0, 2), new Position(5, 1) };
		compareStyleRanges(skippingDigit, getStyleRanges("ThT", "This3Test.txt"));
