	private FindToolbarJob createFinder() {
		if (job != null) {
			job.cancel();
		}
		final String currentPattern = patternField.getText();

		job = new FindToolbarJob(MessageFormat
				.format(UIText.HistoryPage_findbar_find, currentPattern),
				findResults);
		job.pattern = currentPattern;
		job.fileRevisions = fileRevisions;
		job.ignoreCase = caseItem.getSelection();
