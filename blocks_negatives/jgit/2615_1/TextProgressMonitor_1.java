	public void update(final int completed) {
		if (msg == null)
			return;

		final int cmp = lastWorked + completed;
		if (!output && System.currentTimeMillis() - taskBeganAt < 500)
			return;
		if (totalWork == UNKNOWN) {
			display(cmp);
			System.err.flush();
		} else {
			if ((cmp * 100 / totalWork) != (lastWorked * 100) / totalWork) {
				display(cmp);
				System.err.flush();
			}
		}
		lastWorked = cmp;
		output = true;
