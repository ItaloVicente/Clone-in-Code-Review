	public Optional<RefCache> getRefCache() {
		if (refs instanceof InMemoryRefDatabase) {
			return Optional.of((InMemoryRefDatabase) refs);
		}
		return Optional.empty();
	}
