	public void beginAnimatedTask() {
		done();
		progressBar = new ProgressBar(this, styleForProgressBar | SWT.INDETERMINATE);
		requestLayout();
		animated = true;
	}
