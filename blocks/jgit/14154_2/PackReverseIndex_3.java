		final int ith = binarySearch(offset);
		if (ith < 0)
			throw new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().cantFindObjectInReversePackIndexForTheSpecifiedOffset
							Long.valueOf(offset)));

		if (ith + 1 == nth.length)
			return maxOffset;
		return index.getOffset(nth[ith + 1]);
