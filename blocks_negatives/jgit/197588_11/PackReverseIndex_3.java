	public long findNextOffset(long offset, long maxOffset)
			throws CorruptObjectException {
		final int ith = binarySearch(offset);
		if (ith < 0)
			throw new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().cantFindObjectInReversePackIndexForTheSpecifiedOffset,
							Long.valueOf(offset)));
