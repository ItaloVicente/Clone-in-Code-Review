	private ObjectId resolveAbbreviation(final String revstr) throws IOException
			AmbiguousObjectException {
		AbbreviatedObjectId id = AbbreviatedObjectId.fromString(revstr);
		ObjectReader reader = newObjectReader();
		try {
			Collection<ObjectId> matches = reader.resolve(id);
			if (matches.size() == 0)
				return null;
			else if (matches.size() == 1)
				return matches.iterator().next();
			else
				throw new AmbiguousObjectException(id
		} finally {
			reader.release();
		}
	}

