		AbstractTreeIterator aIterator;
		if (a != null) {
			CanonicalTreeParser aParser = new CanonicalTreeParser();
			aParser.reset(reader
			aIterator = aParser;
		} else
			aIterator = new EmptyTreeIterator();
		AbstractTreeIterator bIterator;
		if (b != null) {
			CanonicalTreeParser bParser = new CanonicalTreeParser();
			bParser.reset(reader
			bIterator = bParser;
		} else
			bIterator = new EmptyTreeIterator();
		return scan(aIterator
