		} else {
			if (pattern.length() > 0) {
				patternField.setBackground(errorBackgroundColor);
				currentPositionLabel
						.setText(UIText.HistoryPage_findbar_notFound);
				if (lastErrorPattern == null
						|| (lastErrorPattern != null && !lastErrorPattern
								.startsWith(pattern))) {
					getDisplay().beep();
					nextButton.setEnabled(false);
					previousButton.setEnabled(false);
				}
				lastErrorPattern = pattern;
			} else {
				patternField.setBackground(null);
