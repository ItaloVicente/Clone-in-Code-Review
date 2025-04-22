	public Map<Repository, Collection<String>> getPathsPerRepository() {
		Map<Repository, Collection<String>> result = new HashMap<>();
		for (Map.Entry<Repository, Collection<String>> entry : pathsByRepository
				.entrySet()) {
			result.put(entry.getKey(),
					Collections.unmodifiableCollection(entry.getValue()));
		}
		return Collections.unmodifiableMap(result);
	}

