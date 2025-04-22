
	@Override
	public File gitPrefix() throws IOException {
		String path = SystemReader.getInstance().getenv("PATH");
		String gitExe = scanPath(path
		if (gitExe != null)
			return new File(gitExe).getAbsoluteFile().getParentFile()
					.getParentFile();
		return super.gitPrefix();
	}
