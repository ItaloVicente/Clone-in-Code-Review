	public void setLength(final long sz) {
		if (Integer.MAX_VALUE <= sz)
			throw new IllegalArgumentException(MessageFormat.format(JGitText
					.get().sizeExceeds2GB
		setLength((int) sz);
	}

