	public boolean canExecute(File f) {
		try {
			if (isSymLink(f)) {
				return false;
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		return FileUtil.canExecute(f);
	}

	@Override
	public boolean setExecute(File f
		return FileUtil.setExecute(f
