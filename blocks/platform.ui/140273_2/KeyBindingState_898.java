	private void updateStatusLines() {
		StatusLineContributionItem statusLine = getStatusLine();
		if (statusLine != null) {
			statusLine.setText(getCurrentSequence().format());
		}
	}
