		Git sub2Git = Git.init().setDirectory(submodule2).call();
		assertNotNull(sub2Git);
		Repository sub2 = sub2Git.getRepository();
		assertNotNull(sub2);
		addRepoToClose(sub2);

		write(new File(sub2.getWorkTree(), file), "content");
		sub2Git.add().addFilepattern(file).call();
		RevCommit sub2Head = sub2Git.commit().setMessage("create file").call();
		assertNotNull(sub2Head);

		Repository r = sub1Git.submoduleAdd().setPath(path)
				.setURI(sub2.getDirectory().toURI().toString()).call();
		assertNotNull(r);
		addRepoToClose(r);
		RevCommit sub1Head = sub1Git.commit().setAll(true)
				.setMessage("Adding submodule").call();
		assertNotNull(sub1Head);

		r = git.submoduleAdd().setPath(path)
				.setURI(sub1.getDirectory().toURI().toString()).call();
		assertNotNull(r);
		addRepoToClose(r);
		assertNotNull(git.commit().setAll(true).setMessage("Adding submodule")
				.call());

		File directory = createTempDirectory("testCloneRepositoryWithNestedSubmodules");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setCloneSubmodules(true);
		clone.setURI(git.getRepository().getDirectory().toURI().toString());
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		assertEquals(Constants.MASTER, git2.getRepository().getBranch());
		assertTrue(new File(git2.getRepository().getWorkTree(), path
				+ File.separatorChar + file).exists());
		assertTrue(new File(git2.getRepository().getWorkTree(), path
				+ File.separatorChar + path + File.separatorChar + file)
				.exists());

		SubmoduleStatusCommand status = new SubmoduleStatusCommand(
				git2.getRepository());
		Map<String, SubmoduleStatus> statuses = status.call();
		SubmoduleStatus pathStatus = statuses.get(path);
		assertNotNull(pathStatus);
		assertEquals(SubmoduleStatusType.INITIALIZED, pathStatus.getType());
		assertEquals(sub1Head, pathStatus.getHeadId());
		assertEquals(sub1Head, pathStatus.getIndexId());

		SubmoduleWalk walk = SubmoduleWalk.forIndex(git2.getRepository());
		assertTrue(walk.next());
		Repository clonedSub1 = walk.getRepository();
		assertNotNull(clonedSub1);
		assertEquals(
				new File(git2.getRepository().getWorkTree(), walk.getPath()),
				clonedSub1.getWorkTree());
		assertEquals(new File(new File(git2.getRepository().getDirectory(),
				"modules"), walk.getPath()),
				clonedSub1.getDirectory());
		status = new SubmoduleStatusCommand(clonedSub1);
		statuses = status.call();
		clonedSub1.close();
		pathStatus = statuses.get(path);
		assertNotNull(pathStatus);
		assertEquals(SubmoduleStatusType.INITIALIZED, pathStatus.getType());
		assertEquals(sub2Head, pathStatus.getHeadId());
		assertEquals(sub2Head, pathStatus.getIndexId());
		assertFalse(walk.next());
