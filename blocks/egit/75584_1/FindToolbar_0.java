	private void find(boolean next) {
		if (patternField.getText().length() > 0 && findResults.size() == 0) {
			final FindToolbarJob finder = createFinder();
			finder.setUser(true);
			finder.schedule();
			patternField.setSelection(0, 0);
		} else {
			int currentIx = historyTable.getSelectionIndex();
			int newIx = -1;
			if (next) {
				newIx = findResults.getIndexAfter(currentIx);
				if (newIx == -1) {
					newIx = findResults.getFirstIndex();
				}
			} else {
				newIx = findResults.getIndexBefore(currentIx);
				if (newIx == -1) {
					newIx = findResults.getLastIndex();
				}
			}
			notifyListeners(newIx);

			String current = null;
			currentPosition = findResults.getMatchNumberFor(newIx);
			if (currentPosition == -1) {
				current = "-"; //$NON-NLS-1$
			} else {
				current = String.valueOf(currentPosition);
			}
			currentPositionLabel.setText(current + '/' + findResults.size());
		}
	}

