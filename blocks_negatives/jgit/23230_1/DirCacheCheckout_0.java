	private static byte[][] forbidden;
	static {
		String[] list = getSortedForbiddenFileNames();
		forbidden = new byte[list.length][];
		for (int i = 0; i < list.length; ++i)
			forbidden[i] = Constants.encodeASCII(list[i]);
	}

	static String[] getSortedForbiddenFileNames() {
		String[] list = new String[] { "AUX", "COM1", "COM2", "COM3", "COM4", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				"COM5", "COM6", "COM7", "COM8", "COM9", "CON", "LPT1", "LPT2", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
				"LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9", "NUL", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		return list;
	}

