		if (!done) {
			ObjectReader reader = repo.newObjectReader();
			try {
				return compute(reader
			} finally {
				reader.release();
			}
		}
		return Collections.unmodifiableList(entries);
	}

	public List<DiffEntry> compute(ObjectReader reader
			throws IOException {
