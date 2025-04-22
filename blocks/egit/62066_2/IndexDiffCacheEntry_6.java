		}
		if (repository == null) {
			return false;
		}
		File directory = repository.getDirectory();
		if (directory == null || !directory.exists()) {
