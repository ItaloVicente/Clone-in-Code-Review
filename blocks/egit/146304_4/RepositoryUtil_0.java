	private String lastRefNameForCommitId(Repository repository,
			String refPrefix, String commitId) throws IOException {
		String result = null;
		for (Ref ref : repository.getRefDatabase().getRefsByPrefix(refPrefix)) {
			ObjectId objectId = ref.getObjectId();
			if (objectId != null && objectId.name().equals(commitId)) {
				if (result == null || result.compareTo(ref.getName()) < 0) {
					result = ref.getName();
				}
			}
		}
		return result;
	}

