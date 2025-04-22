		return inserter.insert(build(true
	}

	ObjectId getTreeId() {
		try {
			return new ObjectInserter.Formatter().idFor(build(false
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private TreeFormatter build(boolean insert
			throws IOException {
