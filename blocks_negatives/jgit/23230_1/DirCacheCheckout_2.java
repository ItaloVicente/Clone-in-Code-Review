
		checkValidPathSegment(isWindows, ignCase, raw, ptr, end,
				t.getEntryPathString());
	}

	private static void checkValidPathSegment(boolean isWindows,
			boolean ignCase, byte[] raw, int ptr, int end, String path) {
		int start = ptr;
		while (ptr < end) {
			if (raw[ptr] == '/')
				throw new InvalidPathException(
						JGitText.get().invalidPathContainsSeparator, "/", path); //$NON-NLS-1$
			if (isWindows) {
				if (raw[ptr] == '\\')
					throw new InvalidPathException(
							JGitText.get().invalidPathContainsSeparator,
							"\\", path); //$NON-NLS-1$
				if (raw[ptr] == ':')
					throw new InvalidPathException(
							JGitText.get().invalidPathContainsSeparator,
							":", path); //$NON-NLS-1$
			}
			ptr++;
		}
		if (ptr - start == 1) {
			if (raw[start] == '.')
				throw new InvalidPathException(path);
		} else if (ptr - start == 2) {
			if (raw[start] == '.')
				if (raw[start + 1] == '.')
					throw new InvalidPathException(path);
		} else if (ptr - start == 4) {
			if (raw[start] == '.')
				if (raw[start + 1] == 'g' || (ignCase && raw[start + 1] == 'G'))
					if (raw[start + 2] == 'i'
							|| (ignCase && raw[start + 2] == 'I'))
						if (raw[start + 3] == 't'
								|| (ignCase && raw[start + 3] == 'T'))
							throw new InvalidPathException(path);
		}
		if (isWindows) {
			if (ptr > 0) {
				if (raw[ptr - 1] == '.')
					throw new InvalidPathException(
							JGitText.get().invalidPathPeriodAtEndWindows, path);
				if (raw[ptr - 1] == ' ')
					throw new InvalidPathException(
							JGitText.get().invalidPathSpaceAtEndWindows, path);
			}

			int i;
			for (i = start; i < ptr; ++i)
				if (raw[i] == '.')
					break;
			int len = i - start;
			if (len == 3 || len == 4) {
				for (int j = 0; j < forbidden.length; ++j) {
					if (forbidden[j].length == len) {
						if (toUpper(raw[start]) < forbidden[j][0])
							break;
						int k;
						for (k = 0; k < len; ++k) {
							if (toUpper(raw[start + k]) != forbidden[j][k])
								break;
						}
						if (k == len)
							throw new InvalidPathException(
									JGitText.get().invalidPathReservedOnWindows,
									RawParseUtils.decode(forbidden[j]), path);
					}
				}
			}
