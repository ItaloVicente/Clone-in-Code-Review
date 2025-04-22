	protected String getCurrentBranch() {
		try {
			return repo.getBranch();
		} catch (IOException e) {
			return null;
		}
	}
