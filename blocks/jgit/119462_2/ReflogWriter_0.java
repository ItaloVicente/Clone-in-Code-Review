	private FileOutputStream getFileOutputStream(File log) throws IOException {
		try {
			return new FileOutputStream(log
		} catch (FileNotFoundException err) {
			File dir = log.getParentFile();
			if (dir.exists()) {
				throw err;
			}
			if (!dir.mkdirs() && !dir.isDirectory()) {
				throw new IOException(MessageFormat
						.format(JGitText.get().cannotCreateDirectory
			}
			return new FileOutputStream(log
		}
	}

