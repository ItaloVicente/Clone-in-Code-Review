		if (windows) {
			if (raw[end - 1] == ' ' || raw[end - 1] == '.')
				throw new CorruptObjectException("invalid name ends with '"
						+ ((char) raw[end - 1]) + "'");
			if (end - ptr >= 3)
				checkNotWindowsDevice(raw
		}
	}

	private static void checkNotWindowsDevice(byte[] raw
			throws CorruptObjectException {
		switch (toLower(raw[ptr])) {
			if (end - ptr >= 3
					&& toLower(raw[ptr + 1]) == 'u'
					&& toLower(raw[ptr + 2]) == 'x'
					&& (end - ptr == 3 || raw[ptr + 3] == '.'))
				throw new CorruptObjectException("invalid name 'AUX'");
			break;

			if (end - ptr >= 3
					&& toLower(raw[ptr + 2]) == 'n'
					&& toLower(raw[ptr + 1]) == 'o'
					&& (end - ptr == 3 || raw[ptr + 3] == '.'))
				throw new CorruptObjectException("invalid name 'CON'");
			if (end - ptr >= 4
					&& toLower(raw[ptr + 2]) == 'm'
					&& toLower(raw[ptr + 1]) == 'o'
					&& isPositiveDigit(raw[ptr + 3])
					&& (end - ptr == 4 || raw[ptr + 4] == '.'))
				throw new CorruptObjectException("invalid name 'COM"
						+ ((char) raw[ptr + 3]) + "'");
			break;

			if (end - ptr >= 4
					&& toLower(raw[ptr + 1]) == 'p'
					&& toLower(raw[ptr + 2]) == 't'
					&& isPositiveDigit(raw[ptr + 3])
					&& (end - ptr == 4 || raw[ptr + 4] == '.'))
				throw new CorruptObjectException("invalid name 'LPT"
						+ ((char) raw[ptr + 3]) + "'");
			break;

			if (end - ptr >= 3
					&& toLower(raw[ptr + 1]) == 'u'
					&& toLower(raw[ptr + 2]) == 'l'
					&& (end - ptr == 3 || raw[ptr + 3] == '.'))
				throw new CorruptObjectException("invalid name 'NUL'");
			break;

			if (end - ptr >= 3
					&& toLower(raw[ptr + 1]) == 'r'
					&& toLower(raw[ptr + 2]) == 'n'
					&& (end - ptr == 3 || raw[ptr + 3] == '.'))
				throw new CorruptObjectException("invalid name 'PRN'");
			break;
		}
