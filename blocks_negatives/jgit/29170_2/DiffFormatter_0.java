		CanonicalTreeParser aParser = new CanonicalTreeParser();
		CanonicalTreeParser bParser = new CanonicalTreeParser();

		aParser.reset(reader, a);
		bParser.reset(reader, b);

		return scan(aParser, bParser);
