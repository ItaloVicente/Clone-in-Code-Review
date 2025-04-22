	public String getMessage() {
		return message;
	}

	public IFile[] getFilesToCommit() {
		return filesToCommit;
	}

	public Collection<IFile> getNotIndexedFiles() {
		return this.notIndexed;
	}

	public Collection<IFile> getNotTrackedFiles() {
		return this.notTracked;
	}

	public void setNewFilesToCommit(IFile[] filesToCommit, Collection<IFile> notIndexed, Collection<IFile> notTracked) {
		this.filesToCommit = filesToCommit;
		this.notIndexed = notIndexed;
		this.notTracked = notTracked;
	}

