	@Test
	public void testLsRemoteWithSymRefs() throws Exception {
		File directory = createTempDirectory("testRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setCloneAllBranches(true);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());


		LsRemoteCommand lsRemoteCommand = git2.lsRemote();
		Collection<Ref> refs = lsRemoteCommand.call();
		assertNotNull(refs);
		assertEquals(6

		Optional<Ref> headRef = refs.stream().filter(ref -> ref.getName().equals(Constants.HEAD)).findFirst();
		assertTrue("expected a HEAD Ref"
		assertTrue("expected HEAD Ref to be a Symbolic"
		assertEquals("refs/heads/test"
	}

