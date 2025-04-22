		if (newInput != null) {
			walk = ((CommitFileDiffViewer) newViewer).getTreeWalk();
			commit = (RevCommit) newInput;
		} else {
			walk = null;
			commit = null;
		}
