		return result != null ? result : Collections.emptyList();
	}

	public Optional<T> getFirstResult() {
		if (result != null) {
			return result.stream().findFirst();
		}
		return Optional.empty();
