	@Override
	InMemoryNoteBucket append(Note note) {
		int cell = cell(note);
		InMemoryNoteBucket b = (InMemoryNoteBucket) table[cell];

		if (b == null) {
			LeafBucket n = new LeafBucket(prefixLen + 2);
			table[cell] = n.append(note);
			cnt++;

		} else {
			InMemoryNoteBucket n = b.append(note);
			if (n != b)
				table[cell] = n;
		}
		return this;
	}

