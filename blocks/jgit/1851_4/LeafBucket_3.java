	InMemoryNoteBucket set(AnyObjectId noteOn
			ObjectReader or) throws IOException {
		int p = search(noteOn);
		if (0 <= p) {
			if (noteData != null) {
				notes[p].setData(noteData.copy());
				return this;

			} else {
				System.arraycopy(notes
				cnt--;
				return 0 < cnt ? this : null;
			}

		} else if (noteData != null) {
			growIfFull();
			p = -(p + 1);
			if (p < cnt)
				System.arraycopy(notes
			notes[p] = new Note(noteOn
			cnt++;
			return this;

		} else {
			return this;
		}
	}

