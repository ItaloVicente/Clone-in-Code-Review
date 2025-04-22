	static Collection<RemoteRefUpdate> copyUpdates(
			final Collection<RemoteRefUpdate> refUpdates) throws IOException {
		final Collection<RemoteRefUpdate> copy = new ArrayList<>(
				refUpdates.size());
		for (final RemoteRefUpdate rru : refUpdates)
			copy.add(new RemoteRefUpdate(rru, null));
		return copy;
	}
