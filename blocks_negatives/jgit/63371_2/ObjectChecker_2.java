		int p = match(raw, ptr, author);
		if (p > ptr) {
			if ((ptr = personIdent(raw, p, id)) < 0) {
				throw new CorruptObjectException(
						JGitText.get().corruptObjectInvalidAuthor);
			}
		} else if (!skip(id)) {
			throw new CorruptObjectException(
					JGitText.get().corruptObjectNoAuthor);
		}
