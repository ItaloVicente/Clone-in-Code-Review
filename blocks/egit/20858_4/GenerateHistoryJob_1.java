			if (commitNotFound) {
				if (forcedRedrawsAfterListIsCompleted < 1 && !loadIncrementally)
					page.setWarningTextInUIThread(this);
			}
			else
				updateUI(incomplete);
