	protected static Boolean isGranted(PosixFilePermission p, String umask) {
		char val;
		switch (p) {
		case OTHERS_EXECUTE:
			val = umask.charAt(umask.length() - 1);
			return isExecuteGranted(val);
		case GROUP_EXECUTE:
			val = umask.charAt(umask.length() - 2);
			return isExecuteGranted(val);
		default:
			throw new UnsupportedOperationException(
		}
