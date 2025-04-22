		try (ConfigScope scope = new ConfigScope(repository)) {
			Object value = getItems(repository).computeIfAbsent(
					RepositoryItem.STATE,
					key -> repository.getRepositoryState());
			assert value != null; // Keep the compiler happy.
			return (RepositoryState) value;
		}
