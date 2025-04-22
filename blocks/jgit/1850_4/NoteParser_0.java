	private InMemoryNoteBucket parse() {
		InMemoryNoteBucket r = parseTree();
		r.nonNotes = firstNonNote;
		return r;
	}

