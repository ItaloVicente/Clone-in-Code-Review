		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setCloneSubmodules(true);
		clone.setURI(fileUri());
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		assertEquals(Constants.MASTER, git2.getRepository().getBranch());
		assertTrue(new File(git2.getRepository().getWorkTree(), path
				+ File.separatorChar + file).exists());

		SubmoduleStatusCommand status = new SubmoduleStatusCommand(
				git2.getRepository());
		Map<String, SubmoduleStatus> statuses = status.call();
		SubmoduleStatus pathStatus = statuses.get(path);
		assertNotNull(pathStatus);
		assertEquals(SubmoduleStatusType.INITIALIZED, pathStatus.getType());
		assertEquals(commit, pathStatus.getHeadId());
		assertEquals(commit, pathStatus.getIndexId());

		try (SubmoduleWalk walk = SubmoduleWalk
				.forIndex(git2.getRepository())) {
			assertTrue(walk.next());
			Repository clonedSub1 = walk.getRepository();
			addRepoToClose(clonedSub1);
			assertNotNull(clonedSub1);
			assertEquals(new File(git2.getRepository().getWorkTree(),
					walk.getPath()), clonedSub1.getWorkTree());
			assertEquals(
					new File(new File(git2.getRepository().getDirectory(),
							"modules"), walk.getPath()),
					clonedSub1.getDirectory());
