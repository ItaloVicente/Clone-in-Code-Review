	public Iterator<Note> iterator() {

		try {
			return new Iterator<Note>() {

				private Iterator<Note> itr = root == null ? null : root
						.iterator(new MutableObjectId()

				public boolean hasNext() {
					if (itr == null)
						return true;
					return itr.hasNext();
				}

				public Note next() {
					return itr.next();
				}

				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

