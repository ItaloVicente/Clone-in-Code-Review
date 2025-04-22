				if (!isDisposed()) {
					currentPositionLabel.setText(""); //$NON-NLS-1$
					findNextAction.setEnabled(false);
					findPreviousAction.setEnabled(false);
					currentPositionLabel.requestLayout();
					notifyLayout();
				}
				if (historyTable != null && !historyTable.isDisposed()) {
