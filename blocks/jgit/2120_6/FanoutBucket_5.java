	InMemoryNoteBucket contractIfTooSmall(AnyObjectId noteOn
			throws IOException {
		if (estimateSize(noteOn
			InMemoryNoteBucket r = new LeafBucket(prefixLen);
			for (Iterator<Note> i = iterator(noteOn
				r = r.append(i.next());
			r.nonNotes = nonNotes;
			return r;
		}

		return this;
	}

