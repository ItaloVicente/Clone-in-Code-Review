	@Test
	public void testNestedCommittedGitRepoAndPathFilter() throws Exception {
		commitFile("file.txt"
		try (Repository inner = new FileRepositoryBuilder()
				.setWorkTree(new File(db.getWorkTree()
			inner.create();
			writeTrashFile("subgit/sub.txt"
			try (Git outerGit = new Git(db); Git innerGit = new Git(inner)) {
				innerGit.add().addFilepattern("sub.txt").call();
				innerGit.commit().setMessage("Inner commit").call();
				outerGit.add().addFilepattern("subgit").call();
				outerGit.commit().setMessage("Outer commit").call();
				assertTrue(innerGit.status().call().isClean());
				assertTrue(outerGit.status().call().isClean());
				writeTrashFile("subgit/sub.txt"
				assertFalse(innerGit.status().call().isClean());
				assertFalse(outerGit.status().call().isClean());
				assertTrue(
						outerGit.status().addPath("file.txt").call().isClean());
				assertTrue(outerGit.status().addPath("doesntexist").call()
						.isClean());
				assertFalse(
						outerGit.status().addPath("subgit").call().isClean());
			}
		}
	}

