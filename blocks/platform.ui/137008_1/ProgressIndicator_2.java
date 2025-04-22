	public void beginTask(int max) {
		state++;
		if (state > 2) {
			throw new RuntimeException("beginCalled twice"); //$NON-NLS-1$
		}
		done();
		progressBar = new ProgressBar(this, style);
		this.totalWork = max;
		this.sumWorked = 0;
		progressBar.setMinimum(0);
		progressBar.setMaximum(PROGRESS_MAX);
		progressBar.setSelection(0);

		requestLayout();
		animated = false;
	}

	public void done() {
		if (progressBar != null && !progressBar.isDisposed()) {
			progressBar.dispose();
			progressBar = null;
		}
		requestLayout();
	}

	public void sendRemainingWork() {
		worked(totalWork - sumWorked);
	}
