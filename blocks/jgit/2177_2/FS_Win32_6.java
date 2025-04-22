
	@Override
	public File gitPrefix() {
		String path = SystemReader.getInstance().getenv("PATH");
		File gitExe = searchPath(path
		if (gitExe != null)
			return gitExe.getParentFile().getParentFile();
		return super.gitPrefix();
	}
