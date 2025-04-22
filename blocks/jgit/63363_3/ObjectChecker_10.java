		int p = match(raw
		if (p > ptr) {
			if ((ptr = personIdent(raw
				throw new CorruptObjectException(
						JGitText.get().corruptObjectInvalidAuthor);
			}
		} else if (!skip(id)) {
