		ptr = nextLF(raw, ptr);

		if ((ptr = match(raw, ptr, tagger)) > 0) {
			if ((ptr = personIdent(raw, ptr)) < 0 || raw[ptr++] != '\n')
				throw new CorruptObjectException(
						JGitText.get().corruptObjectInvalidTagger);
