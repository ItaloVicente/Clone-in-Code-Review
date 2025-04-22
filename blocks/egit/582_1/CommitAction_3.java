		performCommit(commitDialog, commitMessage, amending);
	}

	private ArrayList<IFile> getFiles() {
		ArrayList<IFile> candidateList = new ArrayList<IFile>();
		for (List<IFile> candidates : fileCandidates.values()) {
			candidateList.addAll(candidates);
