	public Optional<Boolean> isFetchAtomic() {
		return fetchAtomic;
	}

	public void setFetchAtomic(final boolean atomic) {
		this.fetchAtomic = Optional.of(atomic);
	}

