	@Override
	InMemoryNoteBucket set(AnyObjectId noteOn
			ObjectReader or) throws IOException {
		int cell = cell(noteOn);
		NoteBucket b = table[cell];

		if (b == null) {
			if (noteData == null)
				return this;

			LeafBucket n = new LeafBucket(prefixLen + 2);
			table[cell] = n.set(noteOn
			cnt++;
			return this;

		} else {
			NoteBucket n = b.set(noteOn
			if (n == null) {
				table[cell] = null;
				cnt--;

				if (cnt == 0)
					return null;

				return this;

			} else if (n != b) {
				table[cell] = n;
			}
			return this;
		}
	}

