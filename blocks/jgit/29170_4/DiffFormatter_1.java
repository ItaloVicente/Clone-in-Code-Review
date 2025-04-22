	private AbstractTreeIterator makeIteratorFromTreeOrNull(RevTree tree)
			throws IncorrectObjectTypeException
		if (tree != null) {
			CanonicalTreeParser parser = new CanonicalTreeParser();
			parser.reset(reader
			return parser;
		} else
			return new EmptyTreeIterator();
