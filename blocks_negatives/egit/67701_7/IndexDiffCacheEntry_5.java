	private boolean checkRepository() {
		if (Activator.getDefault() == null) {
			return false;
		}
		if (repository == null) {
			return false;
		}
		File directory = repository.getDirectory();
		if (directory == null || !directory.exists()) {
			return false;
		}
		return true;
	}

