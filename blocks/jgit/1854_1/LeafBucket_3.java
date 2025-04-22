	@Override
	InMemoryNoteBucket append(Note note) {
		if (shouldSplit()) {
			return split().append(note);

		} else {
			growIfFull();
			notes[cnt++] = note;
			return this;
		}
	}

