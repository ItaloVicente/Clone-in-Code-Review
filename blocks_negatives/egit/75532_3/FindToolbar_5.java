	void progressUpdate(int percent) {
		int total = findResults.size();
		currentPositionLabel.setForeground(null);
		if (total > 0) {
			nextButton.setEnabled(true);
			previousButton.setEnabled(true);
			patternField.setBackground(null);
		} else {
			nextButton.setEnabled(false);
			previousButton.setEnabled(false);
		}
		progressBar.setSelection(percent);
		historyTable.clearAll();
	}

	void findCompletionUpdate(String pattern, boolean overflow) {
