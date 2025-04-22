		if ((ptr = match(raw, ptr, tree)) < 0)
			throw new CorruptObjectException(
					JGitText.get().corruptObjectNotreeHeader);
		if ((ptr = id(raw, ptr)) < 0 || raw[ptr++] != '\n')
			throw new CorruptObjectException(
					JGitText.get().corruptObjectInvalidTree);
