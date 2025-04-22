	static {
		String umask = readUmask();

		if (umask != null && umask.length() > 0 && umask.matches("\\d{3,4}")) { //$NON-NLS-1$
			EXECUTE_FOR_OTHERS = isGranted(PosixFilePermission.OTHERS_EXECUTE,
					umask);
			EXECUTE_FOR_GROUP = isGranted(PosixFilePermission.GROUP_EXECUTE,
					umask);
		} else {
			EXECUTE_FOR_OTHERS = null;
			EXECUTE_FOR_GROUP = null;
		}
