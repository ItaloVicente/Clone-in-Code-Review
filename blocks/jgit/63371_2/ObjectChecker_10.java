	private void report(@NonNull ErrorType err
			String why) throws CorruptObjectException {
		if (errors.contains(err)) {
			report(id
		}
	}

	private void report(@Nullable AnyObjectId id
			throws CorruptObjectException {
		if (id == null || skipList == null || !skipList.contains(id)) {
			if (id != null) {
				throw new CorruptObjectException(id
			}
			throw new CorruptObjectException(why);
		}
