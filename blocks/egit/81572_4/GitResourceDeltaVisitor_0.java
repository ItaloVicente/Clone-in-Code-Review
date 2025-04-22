
	private Repository getRepository() {
		if (repository != null) {
			return repository;
		}
		return supplier.get();
	}
