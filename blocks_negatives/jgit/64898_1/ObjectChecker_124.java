		if ((ptr = match(raw, ptr, author)) < 0)
			throw new CorruptObjectException(
					JGitText.get().corruptObjectNoAuthor);
		if ((ptr = personIdent(raw, ptr)) < 0 || raw[ptr++] != '\n')
			throw new CorruptObjectException(
					JGitText.get().corruptObjectInvalidAuthor);
