	@Test
	public void testCloneRepositoryNonStandardRef() throws Exception {
		git.checkout().setName("refs/meta/foo/bar").call();
		git.branchDelete().setBranchNames("test"
				.call();
		git.tagDelete().setTags("tag-for-blob"
		git.gc().setExpire(new Date()).call();

		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository().setDirectory(directory)
				.setURI(fileUri());
		Git git2 = command.call();
		Repository clonedRepo = git2.getRepository();
		addRepoToClose(clonedRepo);

		Map<AnyObjectId
				.getAllRefsByPeeledObjectId();
		assertTrue(allRefs.isEmpty());
		Ref head = clonedRepo.exactRef(Constants.HEAD);
		assertNull(head.getObjectId());
	}

