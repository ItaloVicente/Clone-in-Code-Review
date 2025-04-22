		FormatEntry old = null;
		FormatEntry entry = new FormatEntry(fmt
		while (!replace(formats
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
		}
