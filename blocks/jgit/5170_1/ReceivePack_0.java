	public void setMessageOutputStream(final OutputStream messages) {
		if (msgOut != null)
			throw new IllegalStateException(
					MessageFormat.format(JGitText.get().illegalStateExists
		msgOut = messages;
	}

