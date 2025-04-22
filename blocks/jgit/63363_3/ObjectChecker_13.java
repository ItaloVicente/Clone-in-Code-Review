	@SuppressWarnings("resource")
	@Nullable
	private ObjectId idFor(int objType
		if (skipList != null) {
			return new ObjectInserter.Formatter().idFor(objType
		}
		return null;
	}

	private boolean skip(@Nullable AnyObjectId id) {
		return skipList != null && id != null && skipList.contains(id);
	}

