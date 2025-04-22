	void parseHeaders(final RevWalk walk) throws MissingObjectException,
			IncorrectObjectTypeException, IOException {
		loadCanonical(walk);
		flags |= PARSED;
	}

	void parseBody(final RevWalk walk) throws MissingObjectException,
			IncorrectObjectTypeException, IOException {
		if ((flags & PARSED) == 0)
			parseHeaders(walk);
	}
