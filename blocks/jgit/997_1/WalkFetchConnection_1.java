	private boolean alreadyHave(final AnyObjectId id) throws TransportException {
		try {
			return reader.has(id);
		} catch (IOException error) {
			throw new TransportException(MessageFormat.format(
					JGitText.get().cannotReadObject
		}
	}

