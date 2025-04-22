		return result != null ? result : Collections.emptyList();
	}

	public Optional<T> getFirstResult() {
		Collection<T> list = getResult();
		Iterator<T> iterator = list.iterator();
		if (iterator.hasNext()) {
			return Optional.of(iterator.next());
		}
		return Optional.empty();
