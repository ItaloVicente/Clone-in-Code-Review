		copyFile(JGitTestUtil.getTestResourceFile("packed-refs"), new File(trash_git,packed-refs));
	}

	protected void tearDown() throws Exception {
		RepositoryCache.clear();
		db.close();
		for (Repository r : repositoriesToClose)
			r.close();

		if (packedGitMMAP)
			System.gc();

		final String name = getClass().getName() + "." + getName();
		recursiveDelete(trash, false, name, true);
		for (Repository r : repositoriesToClose)
			recursiveDelete(r.getWorkDir(), false, name, true);
		repositoriesToClose.clear();

		super.tearDown();
	}

	/**
	 * Helper for creating extra empty repos
	 *
	 * @return a new empty git repository for testing purposes
	 *
	 * @throws IOException
	 */
	protected Repository createNewEmptyRepo() throws IOException {
		return createNewEmptyRepo(false);
	}

	/**
	 * Helper for creating extra empty repos
	 *
	 * @param bare if true, create a bare repository.
	 * @return a new empty git repository for testing purposes
	 *
	 * @throws IOException
	 */
	protected Repository createNewEmptyRepo(boolean bare) throws IOException {
		final File newTestRepo = new File(trashParent, "new"
				+ System.currentTimeMillis() + "." + (testcount++)
				+ (bare ? "" : "/") + ".git").getCanonicalFile();
		assertFalse(newTestRepo.exists());
		final Repository newRepo = new Repository(newTestRepo);
		newRepo.create();
		final String name = getClass().getName() + "." + getName();
		shutDownCleanups.add(new Runnable() {
			public void run() {
				recursiveDelete(newTestRepo, false, name, false);
			}
		});
		repositoriesToClose.add(newRepo);
		return newRepo;
