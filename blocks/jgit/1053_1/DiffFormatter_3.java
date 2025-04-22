	public void setAbbreviationLength(final int count) {
		if (count < 0)
			throw new IllegalArgumentException(
					JGitText.get().abbreviationLengthMustBeNonNegative);
		abbreviationLength = count;
	}

	public void setRawTextFactory(RawText.Factory type) {
		rawTextFactory = type;
	}

