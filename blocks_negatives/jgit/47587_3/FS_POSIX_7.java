	/**
	 * @param c
	 *            character representing octal permission value from the table
	 *            in {@link #isGranted(PosixFilePermission, String)}
	 * @return true if the "execute" permission is granted according to given
	 *         character
	 */
	private static Boolean isExecuteGranted(char c) {
		if (c == '0' || c == '2' || c == '4' || c == '6')
			return Boolean.TRUE;
		return Boolean.FALSE;
