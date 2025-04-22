	/**
	 * @return returns the files that could not be added to the index
	 * because there are unmerged entries
	 */
	public Collection<IFile> getNotAddedFiles() {
		return notAddedFiles;
	}

	private void addToIndex(IFile file,
			Collection<GitIndex> changedIndexes,
			Collection<GitIndex> indexesWithUnmergedEntries) throws IOException {
		IProject project = file.getProject();
