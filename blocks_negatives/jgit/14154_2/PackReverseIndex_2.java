		if (offset <= Integer.MAX_VALUE) {
			final int i32 = Arrays.binarySearch(offsets32, (int) offset);
			if (i32 < 0)
				throw new CorruptObjectException(
						MessageFormat.format(
								JGitText.get().cantFindObjectInReversePackIndexForTheSpecifiedOffset,
								Long.valueOf(offset)));

			if (i32 + 1 == offsets32.length) {
				if (offsets64.length > 0)
					return offsets64[0];
				return maxOffset;
			}
			return offsets32[i32 + 1];
		} else {
			final int i64 = Arrays.binarySearch(offsets64, offset);
			if (i64 < 0)
				throw new CorruptObjectException(
						MessageFormat.format(
								JGitText.get().cantFindObjectInReversePackIndexForTheSpecifiedOffset,
								Long.valueOf(offset)));

			if (i64 + 1 == offsets64.length)
				return maxOffset;
			return offsets64[i64 + 1];
		}
