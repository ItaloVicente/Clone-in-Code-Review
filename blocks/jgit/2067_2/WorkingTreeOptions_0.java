	private WorkingTreeOptions(final Config rc) {
		fileMode = rc.getBoolean("core"
		autoCRLF = rc.getEnum("core"
	}

	public boolean isFileMode() {
		return fileMode;
