		writeAllFiles();
		assertUntrackedFiles(command
		addFilesToIndex(git);
		assertStagedFiles(command
		makeInitialCommit(git);
		assertAfterInitialCommit(command
		makeSomeChangesAndStageThem(git);
		assertStagedStatus(command
		createUnmergedFile(git);
		commitPendingChanges(git);
		assertUntracked(command
		checkoutTestBranch(git);
		assertUntracked(command
		RevCommit testBranch = commitChangesInTestBranch(git);
		assertUntracked(command
		checkoutMasterBranch(git);
		changeUnmergedFileAndCommit(git);
		assertUntracked(command
		mergeTestBranchInMaster(git
		assertUntrackedAndUnmerged(command
		detachHead(git);
		assertUntrackedAndUnmerged(command
	}

	private void writeAllFiles() throws IOException {
