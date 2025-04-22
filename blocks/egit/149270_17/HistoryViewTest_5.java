	public void testSelectShownRefs() throws Exception {
		Set<RefFilter> filters = refFilterHelper.getRefFilters();
		filters.add(refFilterHelper.new RefFilter("refs/heads/test1"));
		filters.add(refFilterHelper.new RefFilter("refs/heads/test?"));
		filters.add(refFilterHelper.new RefFilter("refs/heads/test*"));
		refFilterHelper.setRefFilters(filters);

		Repository repo = myRepoViewUtil.lookupRepository(repoFile);

		try (Git git = Git.wrap(repo)) {
			checkout(git, "testD", false);
