
	private boolean hasHead(Repository repo) {
		try {
			return repo.getRef(Constants.HEAD).getObjectId() != null;
		} catch (IOException e) {
			return false;
		}
	}

