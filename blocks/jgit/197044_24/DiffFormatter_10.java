	private RawText openBinary(DiffEntry.Side side
			throws IOException {
		if (entry.getMode(side) == FileMode.MISSING) {
			return RawText.EMPTY_TEXT;
		}

		if (entry.getMode(side).getObjectType() != Constants.OBJ_BLOB) {
			return RawText.EMPTY_TEXT;
		}

		AbbreviatedObjectId id = entry.getId(side);
		if (!id.isComplete()) {
			Collection<ObjectId> ids = reader.resolve(id);
			if (ids.size() == 1) {
				id = AbbreviatedObjectId.fromObjectId(ids.iterator().next());
				switch (side) {
					case OLD:
						entry.oldId = id;
						break;
					case NEW:
						entry.newId = id;
						break;
				}
			} else if (ids.isEmpty()) {
				throw new MissingObjectException(id
			} else {
				throw new AmbiguousObjectException(id
			}
		}

		ObjectLoader ldr = LfsFactory.getInstance().applySmudgeFilter(repository
				source.open(side
		return RawText.loadBinary(ldr);
	}

