		if (!fProgressIndicator.isDisposed()) {
			if (totalWork == IProgressMonitor.UNKNOWN || totalWork == 0) {
				fProgressIndicator.beginAnimatedTask();
			} else {
				fProgressIndicator.beginTask(totalWork);
			}
