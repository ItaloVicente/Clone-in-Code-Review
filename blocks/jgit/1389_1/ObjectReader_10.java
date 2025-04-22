	public AbbreviatedObjectId abbreviate(AnyObjectId objectId)
			throws IOException {
		return abbreviate(objectId
	}

	public AbbreviatedObjectId abbreviate(AnyObjectId objectId
			throws IOException {
		if (len == Constants.OBJECT_ID_STRING_LENGTH)
			return AbbreviatedObjectId.fromObjectId(objectId);

		AbbreviatedObjectId abbrev = objectId.abbreviate(len);
		Collection<ObjectId> matches = resolve(abbrev);
		while (1 < matches.size() && len < Constants.OBJECT_ID_STRING_LENGTH) {
			abbrev = objectId.abbreviate(++len);
			List<ObjectId> n = new ArrayList<ObjectId>(8);
			for (ObjectId candidate : matches) {
				if (abbrev.prefixCompare(candidate) == 0)
					n.add(candidate);
			}
			if (1 < n.size())
				matches = n;
			else
				matches = resolve(abbrev);
		}
		return abbrev;
	}

	public abstract Collection<ObjectId> resolve(AbbreviatedObjectId id)
			throws IOException;

