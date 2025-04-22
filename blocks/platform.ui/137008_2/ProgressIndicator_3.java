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
		if (progressBar.getSelection() < value) {
			progressBar.setSelection(value);
		}
	}

