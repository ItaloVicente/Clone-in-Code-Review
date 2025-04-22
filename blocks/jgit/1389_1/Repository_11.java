
		Ref r = getRefDatabase().getRef(revstr);
		if (r != null)
			return r.getObjectId();

		if (AbbreviatedObjectId.isId(revstr)) {
			AbbreviatedObjectId id = AbbreviatedObjectId.fromString(revstr);
			ObjectReader reader = newObjectReader();
			try {
				Collection<ObjectId> matches = reader.resolve(id);
				if (matches.size() == 1)
					return matches.iterator().next();
			} finally {
				reader.release();
			}
		}

		return null;
