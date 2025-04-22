		FormatEntry old
		do {
			old = formats.get(name);
			if (old == null)
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().archiveFormatAlreadyAbsent
						name));
			if (old.refcnt == 1) {
				entry = null;
				continue;
			}
			entry = new FormatEntry(old.format
		} while (!replace(formats
