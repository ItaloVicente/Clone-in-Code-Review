	private FindToolbarThread createFinder() {
		final FindToolbarThread finder = new FindToolbarThread();
		finder.pattern = patternField.getText();
		finder.fileRevisions = fileRevisions;
		finder.toolbar = this;
		finder.ignoreCase = caseItem.getSelection();
