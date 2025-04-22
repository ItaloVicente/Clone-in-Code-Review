	@Override
	Iterator<Note> iterator(AnyObjectId objId
			throws IOException {
		final MutableObjectId id = new MutableObjectId();
		id.fromObjectId(objId);

		return new Iterator<Note>() {
			private int cell;

			private Iterator<Note> itr;

			public boolean hasNext() {
				if (itr != null && itr.hasNext())
					return true;

				for (; cell < table.length; cell++) {
					NoteBucket b = table[cell];
					if (b == null)
						continue;

					try {
						id.setByte(prefixLen >> 1
						itr = b.iterator(id
					} catch (IOException err) {
						throw new RuntimeException(err);
					}

					if (itr.hasNext()) {
						cell++;
						return true;
					}
				}
				return false;
			}

			public Note next() {
				if (hasNext())
					return itr.next();
				else
					throw new NoSuchElementException();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

