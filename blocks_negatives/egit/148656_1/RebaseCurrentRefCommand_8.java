		return state == RepositoryState.SAFE && hasHead(repo);
	}

	private static boolean hasHead(Repository repo) {
		try {
			Ref headRef = repo.exactRef(Constants.HEAD);
			return headRef != null && headRef.getObjectId() != null;
		} catch (IOException e) {
			return false;
		}
