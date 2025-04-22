		RevCommit commit = (RevCommit) getSelection(getPage()).getFirstElement();
		RevCommit newHead;

		RevertCommand revert;
		Git git = new Git(repo);
		try {
			revert = git.revert().include(commit.getId());
			newHead = revert.call();
			if (newHead != null && revert.getRevertedRefs().isEmpty())
				MessageDialog.openWarning(getPart(event).getSite().getShell(),
