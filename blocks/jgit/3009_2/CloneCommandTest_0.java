	@Test
	public void testCloneRepositoryOnlyOneBranch() {
		try {
			File directory = createTempDirectory("testCloneRepositoryWithBranch");
			CloneCommand command = Git.cloneRepository();
			command.setBranch("refs/heads/master");
			command.setBranchesToClone(Collections
					.singletonList("refs/heads/master"));
			command.setDirectory(directory);
					+ git.getRepository().getWorkTree().getPath());
			Git git2 = command.call();
			assertNotNull(git2);
			assertEquals(git2.getRepository().getFullBranch()
					"refs/heads/master");
			assertEquals(1
					.call().size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

