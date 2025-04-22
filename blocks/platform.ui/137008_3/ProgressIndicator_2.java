	public void beginTask(int max) {
		if (progressBar == null || progressBar.isDisposed()) {
			progressBar = new ProgressBar(this, styleForProgressBar);
			requestLayout();
		}

		this.totalWork = max;
		this.sumWorked = 0;
		progressBar.setMinimum(0);
		progressBar.setMaximum(PROGRESS_MAX);
		progressBar.setSelection(0);

		animated = false;
	}

	public void done() {
		if ((progressBar.getStyle() & SWT.INDETERMINATE) != 0) {
			progressBar.setMinimum(0);
			progressBar.setMaximum(0);
			progressBar.setSelection(0);
		}
		requestLayout();
	}

	public void sendRemainingWork() {
		worked(totalWork - sumWorked);
	}
