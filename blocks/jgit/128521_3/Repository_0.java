	public final Set<ObjectId> getAdditionalHaves() {
		try {
			return getRefDatabase().getAdditionalHaves();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
