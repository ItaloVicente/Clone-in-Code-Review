	private void setNotFoundBackgroundColor() {
		patternField.setData(CCS_CLASS_KEY, NO_RESULTS_CLASS);
		patternField.reskin(SWT.ALL);
		noResults = true;
	}

	private void setNormalBackgroundColor() {
		if (noResults) {
			Color currentColor = patternField.getBackground();
			patternField.setData(CCS_CLASS_KEY, null);
			patternField.reskin(SWT.ALL);
			if (currentColor.equals(patternField.getBackground())) {
				patternField.setBackground(null);
			}
			noResults = false;
		}
	}

