	NoteBucket getBucket(int cell) {
		return table[cell];
	}

	static InMemoryNoteBucket loadIfLazy(NoteBucket b
			ObjectReader or) throws IOException {
		if (b == null)
			return null;
		if (b instanceof InMemoryNoteBucket)
			return (InMemoryNoteBucket) b;
		return ((LazyNoteBucket) b).load(prefix
	}

