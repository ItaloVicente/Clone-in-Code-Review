	private InterruptedIOException readTimedOut(InterruptedIOException e) {
		InterruptedIOException interrupted = new InterruptedIOException(
				MessageFormat.format(JGitText.get().readTimedOut
						Integer.valueOf(timeout)));
		interrupted.initCause(e);
		return interrupted;
