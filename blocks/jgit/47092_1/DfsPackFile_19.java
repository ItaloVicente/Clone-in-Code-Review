	private IOException packfileIsTruncated() {
		invalid = true;
		return new IOException(MessageFormat.format(
				JGitText.get().packfileIsTruncated
	}

