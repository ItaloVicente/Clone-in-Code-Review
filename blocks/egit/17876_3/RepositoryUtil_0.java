
	public static boolean isDetachedHead(Repository repository) {
		try {
			return ObjectId.isId(repository.getFullBranch());
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}
		return false;
	}
