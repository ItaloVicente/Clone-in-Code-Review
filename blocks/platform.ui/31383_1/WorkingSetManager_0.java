			} finally {
				restoreInProgress = false;
			}

			if (savePending) {
				saveState();
				savePending = false;
