		if (AbbreviatedObjectId.isId(revstr)) {
			AbbreviatedObjectId id = AbbreviatedObjectId.fromString(revstr);
			ObjectReader reader = newObjectReader();
			try {
				Collection<ObjectId> matches = reader.resolve(id);
				if (matches.size() == 1)
					return matches.iterator().next();
				if (1 < matches.size())
					throw new AmbiguousObjectException(id, matches);
			} finally {
				reader.release();
			}
		}
