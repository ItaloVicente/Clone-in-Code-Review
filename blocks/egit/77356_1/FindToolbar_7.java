				if (!isDisposed()) {
					findNextAction.setEnabled(false);
					findPreviousAction.setEnabled(false);
					notifyStatus(""); //$NON-NLS-1$
				}
				if (historyTable != null && !historyTable.isDisposed()) {
