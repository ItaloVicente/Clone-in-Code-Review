		try (
				Git git2 = Git.cloneRepository()
						.setDirectory(directory)
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
			assertNotNull(db2.resolve("tag-for-blob"));
			assertEquals(db2.getFullBranch()
			assertEquals("origin"
					db2.getConfig().getString("branch"
			assertEquals("refs/heads/test"
					db2.getConfig().getString("branch"
			assertEquals(2
					git2.branchList()
							.setListMode(ListMode.REMOTE)
							.call()
							.size());
					fetchRefSpec(db2));
		}
