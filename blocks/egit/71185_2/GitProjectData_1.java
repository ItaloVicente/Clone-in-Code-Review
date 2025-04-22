		boolean allMapped = true;
		Iterator<RepositoryMapping> iterator = mappings.values().iterator();
		while (iterator.hasNext()) {
			RepositoryMapping m = iterator.next();
			if (!map(m)) {
				iterator.remove();
				allMapped = false;
			}
