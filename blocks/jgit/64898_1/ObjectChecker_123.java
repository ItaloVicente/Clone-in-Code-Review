	@SuppressWarnings("resource")
	@Nullable
	private ObjectId idFor(int objType
		if (skipList != null) {
			return new ObjectInserter.Formatter().idFor(objType
		}
		return null;
	}

	private void report(@NonNull ErrorType err
			String why) throws CorruptObjectException {
		if (errors.contains(err)
				&& (id == null || skipList == null || !skipList.contains(id))) {
			if (id != null) {
				throw new CorruptObjectException(err
			}
			throw new CorruptObjectException(why);
		}
	}

