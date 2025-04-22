		p = match(raw, ptr, committer);
		if (p > ptr) {
			if ((ptr = personIdent(raw, p, id)) < 0) {
				throw new CorruptObjectException(
						JGitText.get().corruptObjectInvalidCommitter);
			}
		} else if (!skip(id)) {
			throw new CorruptObjectException(
