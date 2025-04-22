	private boolean localHasObject(ObjectId id) throws TransportException {
		try {
			return transport.local.getObjectDatabase().has(id);
		} catch (IOException err) {
			throw new TransportException(
					MessageFormat.format(
							JGitText.get().readingObjectsFromLocalRepositoryFailed
							err.getMessage())
					err);
		}
	}

