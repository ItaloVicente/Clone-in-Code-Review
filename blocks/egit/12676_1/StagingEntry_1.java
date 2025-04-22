	public File getSystemFile() {
		return new File(getLocation().toOSString());
	}

	public StagingFolderEntry getParent() {
		return parent;
	}

	public void setParent(StagingFolderEntry parent) {
		this.parent = parent;
	}

