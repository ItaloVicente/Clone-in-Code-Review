	public void beginAnimatedTask() {
		state++;
		if (state > 2) {
			throw new RuntimeException("beginCalled twice"); //$NON-NLS-1$
		}
		done();
		progressBar = new ProgressBar(this, style | SWT.INDETERMINATE);
		requestLayout();
		animated = true;
	}
