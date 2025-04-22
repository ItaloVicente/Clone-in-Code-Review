	public static void registerFormat(String name, Format<?> fmt) {

		if (formats.putIfAbsent(name, fmt) != null)
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().archiveFormatAlreadyRegistered,
					name));
