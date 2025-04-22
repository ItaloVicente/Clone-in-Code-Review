
	private void createInitialCommit() throws IOException, NoHeadException, NoMessageException, ConcurrentRefUpdateException, JGitInternalException, WrongRepositoryStateException {
		String repoPath = project.getProject().getWorkspace().getRoot().getLocation().toOSString();
		File file = new File(repoPath, "dummy");
		file.createNewFile();
		track(file);
		commit("testBranchOperation\n\nfirst commit\n");
	}

	private void createBranch(String refName, String newRefName) throws IOException {
		RefUpdate updateRef;
		updateRef = repository.updateRef(newRefName);
		Ref startRef = repository.getRef(refName);
		ObjectId startAt = repository.resolve(refName);
		String startBranch;
		if (startRef != null)
			startBranch = refName;
		else
			startBranch = startAt.name();
		startBranch = repository.shortenRefName(startBranch);
		updateRef.setNewObjectId(startAt);
		updateRef.setRefLogMessage("branch: Created from " + startBranch, false); //$NON-NLS-1$
		updateRef.update();
	}

	private void track(File file) throws IOException {
		GitIndex index = repository.getIndex();
		Entry entry = index.add(repository.getWorkDir(), file);
		entry.setAssumeValid(false);
		index.write();
	}

	private void commit(String message) throws NoHeadException, NoMessageException, UnmergedPathException, ConcurrentRefUpdateException, JGitInternalException, WrongRepositoryStateException {
		Git git = new Git(repository);
		CommitCommand commitCommand = git.commit();
		commitCommand.setAuthor("J. Git", "j.git@egit.org");
		commitCommand.setCommitter(commitCommand.getAuthor());
		commitCommand.setMessage(message);
		commitCommand.call();
	}

