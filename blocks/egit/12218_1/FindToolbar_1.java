	private FindToolbarThread createFinder() {
		final FindToolbarThread finder = new FindToolbarThread();
		finder.pattern = patternField.getText();
		finder.fileRevisions = fileRevisions;
		finder.toolbar = this;
		finder.ignoreCase = caseItem.getSelection();
		if (allItem.getSelection()) {
			finder.findInCommitId = true;
			finder.findInComments = true;
			finder.findInAuthor = true;
			finder.findInCommitter = true;
		} else {
			finder.findInCommitId = commitIdItem.getSelection();
			finder.findInComments = commentsItem.getSelection();
			finder.findInAuthor = authorItem.getSelection();
			finder.findInCommitter = committerItem.getSelection();
		}
		return finder;
	}

