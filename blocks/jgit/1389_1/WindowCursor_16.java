	@Override
	public Collection<ObjectId> resolve(AbbreviatedObjectId id)
			throws IOException {
		if (id.isComplete())
			return Collections.singleton(id.toObjectId());
		HashSet<ObjectId> matches = new HashSet<ObjectId>(4);
		db.resolve(matches
		return matches;
	}

