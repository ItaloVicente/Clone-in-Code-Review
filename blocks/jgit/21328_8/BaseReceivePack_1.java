
	public void setMaxPackSizeLimit(final long limit) {
		if (limit < 0)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().receivePackInvalidLimit
		maxPackSizeLimit = limit;
	}

