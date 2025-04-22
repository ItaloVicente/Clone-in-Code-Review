	private IFindListener createFindListener() {
		return new IFindListener() {

			private static final long UPDATE_INTERVAL = 200L; // ms

			private long lastUpdate = 0L;

			@Override
			public void itemAdded(int index, RevObject rev) {
				long now = System.currentTimeMillis();
				if (now - lastUpdate > UPDATE_INTERVAL) {
					final boolean firstUpdate = lastUpdate == 0L;
					lastUpdate = now;
					getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							int total = findResults.size();
							currentPositionLabel.setForeground(null);
							if (total > 0) {
								if (currentPosition == -1) {
									currentPositionLabel.setText("-/" + total); //$NON-NLS-1$
								} else {
									currentPositionLabel.setText(
											Integer.toString(currentPosition)
													+ '/' + total);
								}
								nextButton.setEnabled(true);
								previousButton.setEnabled(true);
								patternField.setBackground(null);
								if (firstUpdate) {
									historyTable.clearAll();
								}
							} else {
								clear();
							}
						}
					});
				}
			}

			@Override
			public void cleared() {
				lastUpdate = 0L;
				if (Display.getCurrent() == null) {
					getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							clear();
						}
					});
				} else {
					clear();
				}
			}

			private void clear() {
				currentPosition = -1;
				currentPositionLabel.setText(""); //$NON-NLS-1$
				nextButton.setEnabled(false);
				previousButton.setEnabled(false);
				if (historyTable != null) {
					historyTable.clearAll();
				}
			}
		};
	}
