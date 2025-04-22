	private void performCommit(CommitDialog commitDialog, String commitMessage)
			throws TeamException {

		IFile[] selectedItems = commitDialog.getSelectedFiles();

		HashMap<Repository, Tree> treeMap = new HashMap<Repository, Tree>();
		try {
			prepareTrees(selectedItems, treeMap);
		} catch (IOException e) {
			throw new TeamException(UIText.CommitAction_errorPreparingTrees, e);
		}

		try {
			doCommits(commitDialog, commitMessage, treeMap);
		} catch (IOException e) {
			throw new TeamException(UIText.CommitAction_errorCommittingChanges, e);
		}
		for (IProject proj : getProjectsForSelectedResources()) {
			RepositoryMapping.getMapping(proj).fireRepositoryChanged();
		}
	}

	private void doCommits(CommitDialog commitDialog, String commitMessage,
			HashMap<Repository, Tree> treeMap) throws IOException, TeamException {

