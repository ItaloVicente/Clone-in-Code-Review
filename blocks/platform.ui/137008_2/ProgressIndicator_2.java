	public void beginTask(int max) {
		done();
		progressBar = new ProgressBar(this, styleForProgressBar);
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
