		if (patternField.getText().length() > 0) {
			patternField.selectAll();
			nextButton.setEnabled(true);
			previousButton.setEnabled(true);
		} else {
			nextButton.setEnabled(false);
			previousButton.setEnabled(false);
		}
		progressBar.setSelection(0);
