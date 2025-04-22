		if ((style & SWT.VERTICAL) == 0)
			style |= SWT.HORIZONTAL;

		determinateProgressBar = new ProgressBar(this, style);
		indeterminateProgressBar = new ProgressBar(this, style
				| SWT.INDETERMINATE);
		layout = new StackLayout();
		setLayout(layout);
	}

	public void beginAnimatedTask() {
		done();
		layout.topControl = indeterminateProgressBar;
		requestLayout();
		animated = true;
	}

	public void beginTask(int max) {
		done();
		this.totalWork = max;
		this.sumWorked = 0;
		determinateProgressBar.setMinimum(0);
		determinateProgressBar.setMaximum(PROGRESS_MAX);
		determinateProgressBar.setSelection(0);
		layout.topControl = determinateProgressBar;
		requestLayout();
		animated = false;
	}

	public void done() {
		if (!animated) {
			determinateProgressBar.setMinimum(0);
			determinateProgressBar.setMaximum(0);
			determinateProgressBar.setSelection(0);
		}
		layout.topControl = null;
		requestLayout();
	}

	public void sendRemainingWork() {
		worked(totalWork - sumWorked);
	}

	public void worked(double work) {
		if (work == 0 || animated) {
			return;
		}
		sumWorked += work;
		if (sumWorked > totalWork) {
			sumWorked = totalWork;
		}
		if (sumWorked < 0) {
			sumWorked = 0;
		}
		int value = (int) (sumWorked / totalWork * PROGRESS_MAX);
		if (determinateProgressBar.getSelection() < value) {
			determinateProgressBar.setSelection(value);
		}
	}

