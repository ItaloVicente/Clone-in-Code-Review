	private int id(final byte[] raw, final int ptr) {
		try {
			tempId.fromString(raw, ptr);
			return ptr + Constants.OBJECT_ID_STRING_LENGTH;
		} catch (IllegalArgumentException e) {
			return -1;
		}
	}

	private int personIdent(final byte[] raw, int ptr) {
		final int emailB = nextLF(raw, ptr, '<');
		if (emailB == ptr || raw[emailB - 1] != '<')
			return -1;

		final int emailE = nextLF(raw, emailB, '>');
		if (emailE == emailB || raw[emailE - 1] != '>')
			return -1;
		if (emailE == raw.length || raw[emailE] != ' ')
			return -1;

		parseBase10(raw, emailE + 1, ptrout); // when
		ptr = ptrout.value;
		if (emailE + 1 == ptr)
			return -1;
		if (ptr == raw.length || raw[ptr] != ' ')
			return -1;

		parseBase10(raw, ptr + 1, ptrout); // tz offset
		if (ptr + 1 == ptrout.value)
			return -1;
		return ptrout.value;
	}

	/**
	 * Check a commit for errors.
	 *
	 * @param raw
	 *            the commit data. The array is never modified.
	 * @throws CorruptObjectException
	 *             if any error was detected.
	 */
	public void checkCommit(final byte[] raw) throws CorruptObjectException {
		int ptr = 0;

		if ((ptr = match(raw, ptr, tree)) < 0)
			throw new CorruptObjectException("no tree header");
		if ((ptr = id(raw, ptr)) < 0 || raw[ptr++] != '\n')
			throw new CorruptObjectException("invalid tree");

		while (match(raw, ptr, parent) >= 0) {
			ptr += parent.length;
			if ((ptr = id(raw, ptr)) < 0 || raw[ptr++] != '\n')
				throw new CorruptObjectException("invalid parent");
		}

		if ((ptr = match(raw, ptr, author)) < 0)
			throw new CorruptObjectException("no author");
		if ((ptr = personIdent(raw, ptr)) < 0 || raw[ptr++] != '\n')
			throw new CorruptObjectException("invalid author");
