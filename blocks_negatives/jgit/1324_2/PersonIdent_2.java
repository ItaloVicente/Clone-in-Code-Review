		final int lt = in.indexOf('<');
		if (lt == -1) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().malformedpersonIdentString, in));
		}
		final int gt = in.indexOf('>', lt);
		if (gt == -1) {
