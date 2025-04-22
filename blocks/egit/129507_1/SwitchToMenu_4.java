	private String getBranch(Repository repo) {
		try {
			return repo.getBranch();
		} catch (IOException e) {
			return ""; //$NON-NLS-1$
		}
	}

