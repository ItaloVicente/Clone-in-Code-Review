	private InterruptedIOException writeTimedOut(InterruptedIOException cause) {
		InterruptedIOException e = new InterruptedIOException(
				MessageFormat.format(JGitText.get().writeTimedOut
						Integer.valueOf(timeout)));
		e.initCause(cause);
		return e;
