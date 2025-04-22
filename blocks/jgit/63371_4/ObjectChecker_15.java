	private void report(@NonNull ErrorType err
			String why) throws CorruptObjectException {
		if (errors.contains(err)
				&& (id == null || skipList == null || !skipList.contains(id))) {
			if (id != null) {
				throw new CorruptObjectException(err
			}
			throw new CorruptObjectException(why);
		}
