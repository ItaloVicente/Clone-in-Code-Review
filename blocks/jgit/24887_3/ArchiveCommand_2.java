		FormatEntry old
		do {
			old = formats.get(name);
			if (old == null) {
				entry = new FormatEntry(fmt
				continue;
			}
			if (!old.format.equals(fmt))
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().archiveFormatAlreadyRegistered
						name));
			entry = new FormatEntry(old.format
		} while (!replace(formats
