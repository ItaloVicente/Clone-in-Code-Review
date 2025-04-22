		return inserter.insert(build());
	}

	@Override
	ObjectId getTreeId() {
		return new ObjectInserter.Formatter().idFor(build());
	}

	private TreeFormatter build() {
