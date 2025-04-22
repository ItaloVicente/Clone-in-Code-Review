	private static final Boolean EXECUTE_FOR_OTHERS;

	private static final Boolean EXECUTE_FOR_GROUP;

	static {
		String umask = readUmask();

		if (umask != null && umask.length() > 0 && umask.matches("\\d{3
			EXECUTE_FOR_OTHERS = isGranted(PosixFilePermission.OTHERS_EXECUTE
					umask);
			EXECUTE_FOR_GROUP = isGranted(PosixFilePermission.GROUP_EXECUTE
					umask);
		} else {
			EXECUTE_FOR_OTHERS = null;
			EXECUTE_FOR_GROUP = null;
		}
	}

