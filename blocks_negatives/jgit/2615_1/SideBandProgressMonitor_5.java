	public void update(final int completed) {
		if (msg == null)
			return;

		final int cmp = lastWorked + completed;
		final long now = System.currentTimeMillis();
		if (!output && now - taskBeganAt < 500)
			return;
		if (totalWork == UNKNOWN) {
			if (now - lastOutput >= 500) {
				display(cmp, null);
				lastOutput = now;
			}
		} else {
			if ((cmp * 100 / totalWork) != (lastWorked * 100) / totalWork
					|| now - lastOutput >= 500) {
				display(cmp, null);
				lastOutput = now;
			}
		}
		lastWorked = cmp;
		output = true;
