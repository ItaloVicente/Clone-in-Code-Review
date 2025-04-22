	public void setSelectedFiles(IFile[] items) {
		Collections.addAll(selectedFiles, items);
	}

	/**
	 * @return the resources selected by the user to commit.
	 */
	public IFile[] getSelectedFiles() {
		return selectedFiles.toArray(new IFile[0]);
