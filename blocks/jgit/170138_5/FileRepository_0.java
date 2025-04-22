	public FileStore getFileStore() {
		if (store == null && getDirectory().exists()) {
			try {
				this.store = Files.getFileStore(getDirectory().toPath());
			} catch (IOException e) {
				throw new JGitInternalException(e.getMessage()
			}
