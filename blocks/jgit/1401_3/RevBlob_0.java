
	@Override
	void parseHeaders(RevWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		if (walk.reader.has(this))
			flags |= PARSED;
		else
			throw new MissingObjectException(this
	}

	@Override
	void parseBody(RevWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		if ((flags & PARSED) == 0)
			parseHeaders(walk);
	}
