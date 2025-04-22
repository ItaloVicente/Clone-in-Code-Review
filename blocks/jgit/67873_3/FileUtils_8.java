	public static File getCommonDir(File dir) throws IOException {
		File commonDir = null;
		File commonDirFile = new File(dir
		if (commonDirFile.isFile()) {
			String commonDirPath = new String(IO.readFully(commonDirFile))
					.trim();
			commonDir = new File(commonDirPath);
			if (!commonDir.isAbsolute()) {
				commonDir = new File(dir
			}
		}
		return commonDir;
	}
