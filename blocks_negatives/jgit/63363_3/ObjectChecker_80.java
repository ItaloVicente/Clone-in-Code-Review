
		if (macosx && isMacHFSGit(raw, ptr, end))
			throw new CorruptObjectException(String.format(
					JGitText.get().corruptObjectInvalidNameIgnorableUnicode,
					RawParseUtils.decode(raw, ptr, end)));

		if (windows) {
			if (raw[end - 1] == ' ' || raw[end - 1] == '.')
