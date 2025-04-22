	private Repository cloneWithoutCloningSubmodule() throws Exception {
		File directory = createTempDirectory("testCloneWithoutCloningSubmodules");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setCloneSubmodules(false);
		clone.setURI(Git.wrap(db).getRepository().getDirectory().toURI().toString());
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		return git2.getRepository();
	}

	@Theory
	public void testUncheckedOutSubmodulesDoNotShowUp(IgnoreSubmoduleMode mode)
			throws Exception {
		Repository db2 = cloneWithoutCloningSubmodule();
		IndexDiff indexDiff = new IndexDiff(db2
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertFalse(indexDiff.diff());
	}

