		CherryPickResult cherryPickResult;
		Git git = new Git(repo);
		Shell parent = getPart(event).getSite().getShell();
		try {
			cherryPickResult = git.cherryPick().include(commit.getId()).call();
			newHead = cherryPickResult.getNewHead();
			if (newHead != null && cherryPickResult.getCherryPickedRefs().isEmpty())
				MessageDialog.openWarning(parent,
