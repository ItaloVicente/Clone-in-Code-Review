	@Override
	Iterator<Note> iterator(AnyObjectId objId
		return new Iterator<Note>() {
			private int idx;

			public boolean hasNext() {
				return idx < cnt;
			}

			public Note next() {
				if (hasNext())
					return notes[idx++];
				else
					throw new NoSuchElementException();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

