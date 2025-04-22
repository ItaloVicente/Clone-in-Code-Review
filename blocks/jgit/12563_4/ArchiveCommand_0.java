	public static void registerFormat(String name
		if (formats.putIfAbsent(name
			throw new JGitInternalException(MessageFormat.format(
					CLIText.get().archiveFormatAlreadyRegistered
					name));
	}

	public static void unregisterFormat(String name) {
		if (formats.remove(name) == null)
			throw new JGitInternalException(MessageFormat.format(
					CLIText.get().archiveFormatAlreadyAbsent
					name));
