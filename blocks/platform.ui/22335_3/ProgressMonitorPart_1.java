		fTaskName = name;
		fSubTaskName = ""; //$NON-NLS-1$
		updateLabel();
		if (totalWork == IProgressMonitor.UNKNOWN || totalWork == 0) {
			fProgressIndicator.beginAnimatedTask();
		} else {
			fProgressIndicator.beginTask(totalWork);
		}
		if (fToolBar != null && !fToolBar.isDisposed()) {
			fToolBar.setVisible(true);
			fToolBar.setFocus();
		}
	}

	@Override
