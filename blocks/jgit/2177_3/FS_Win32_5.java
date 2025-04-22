
	@Override
	public File gitPrefix() {
		String path = SystemReader.getInstance().getenv("PATH");
		File gitExe = searchPath(path
		if (gitExe != null)
			return gitExe.getParentFile().getParentFile();

		String w = readPipe(userHome()
				new String[] { "bash"
				Charset.defaultCharset().name());
		if (w != null)
			return new File(w).getParentFile().getParentFile();

		return null;
	}
