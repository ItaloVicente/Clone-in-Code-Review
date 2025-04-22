		RepositoryState state = UnitOfWork.get(repository, () -> {
			Object value = getItems(repository).computeIfAbsent(
					RepositoryItem.STATE,
					key -> repository.getRepositoryState());
			return (RepositoryState) value;
		});
		assert state != null; // Keep the compiler happy.
		return state;
