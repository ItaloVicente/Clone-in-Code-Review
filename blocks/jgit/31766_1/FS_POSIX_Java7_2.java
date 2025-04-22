	private static final Boolean EXECUTE_FOR_OTHERS;

	static {
		String umask = readUmask();

		if (umask != null && umask.length() > 0 && umask.matches("\\d{3
			EXECUTE_FOR_OTHERS = isSet(PosixFilePermission.OTHERS_EXECUTE
					umask);
		else
			EXECUTE_FOR_OTHERS = null;

	}

