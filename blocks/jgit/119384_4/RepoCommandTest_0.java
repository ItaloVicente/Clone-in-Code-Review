	private Repository cloneRepository(Repository repo
			throws Exception {
		Repository r = Git.cloneRepository()
				.setURI(repo.getDirectory().toURI().toString())
				.setDirectory(createUniqueTestGitDir(true)).setBare(bare).call()
				.getRepository();
		if (bare) {
			assertTrue(r.isBare());
		} else {
			assertFalse(r.isBare());
		}
		return r;
	}

