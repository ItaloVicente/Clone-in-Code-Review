	private FindToolbarJob createFinder() {
		if (job != null) {
			job.cancel();
		}
		final String currentPattern = patternField.getText();

		job = new FindToolbarJob(MessageFormat
				.format(UIText.HistoryPage_findbar_find, currentPattern));
		job.pattern = currentPattern;
		job.fileRevisions = fileRevisions;
		job.toolbar = this;
		job.ignoreCase = caseItem.getSelection();
