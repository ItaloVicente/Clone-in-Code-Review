
	/**
	 * Kick the timestamp of a local file.
	 * <p>
	 * We shouldn't have to make these method calls. The cache is using file
	 * system timestamps, and on many systems unit tests run faster than the
	 * modification clock. Dumping the cache after we make an edit behind
	 * RefDirectory's back allows the tests to pass.
	 *
	 * @param name
	 *            the file in the repository to force a time change on.
	 */
	private void BUG_WorkAroundRacyGitIssues(String name) {
		File path = new File(diskRepo.getDirectory(), name);
		long old = path.lastModified();
		path.setLastModified(set);
		assertTrue("time changed", old != path.lastModified());
	}
