		final Listener findButtonsListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (patternField.getText().length() > 0
						&& findResults.size() == 0) {
					final FindToolbarJob finder = createFinder();
					finder.setUser(true);
					finder.schedule();
					patternField.setSelection(0, 0);
				} else {
					int currentIx = historyTable.getSelectionIndex();
					int newIx = -1;
					if (event.widget == nextButton) {
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
					sendEvent(event.widget, newIx);

					String current = null;
					currentPosition = findResults.getMatchNumberFor(newIx);
					if (currentPosition == -1) {
					} else {
						current = String.valueOf(currentPosition);
					}
					currentPositionLabel
							.setText(current + '/' + findResults.size());
				}
			}
		};
		nextButton.addListener(SWT.Selection, findButtonsListener);
		previousButton.addListener(SWT.Selection, findButtonsListener);

