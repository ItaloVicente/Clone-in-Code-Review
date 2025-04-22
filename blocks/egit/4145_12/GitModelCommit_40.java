	public String getName() {
		return commit.getShortMessage();
	}

	@Override
	public GitModelObject[] getChildren() {
		List<GitModelObject> result = new ArrayList<GitModelObject>();

		if (commit.getChildren() != null) // prevent from NPE in empty commits
			for (Entry<String, Change> cacheEntry : commit.getChildren().entrySet()) {
				GitModelObject nested = addChild(cacheEntry.getValue(), cacheEntry.getKey());
				if (nested != null)
					result.add(nested);
			}

		return result.toArray(new GitModelObject[result.size()]);
	}

	public Commit getCachedCommitObj() {
		return commit;
