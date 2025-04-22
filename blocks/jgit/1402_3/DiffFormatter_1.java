		if (!id.isComplete()) {
			Collection<ObjectId> ids = reader.resolve(id);
			if (ids.size() == 1)
				id = AbbreviatedObjectId.fromObjectId(ids.iterator().next());
			else if (ids.size() == 0)
				throw new MissingObjectException(id
			else
				throw new AmbiguousObjectException(id
		}
