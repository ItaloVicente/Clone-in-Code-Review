
	public static void createSymLink(File path
			throws IOException {
		FS.DETECTED.createSymLink(path
	}

	public static String readSymLink(File path) throws IOException {
		return FS.DETECTED.readSymLink(path);
	}
