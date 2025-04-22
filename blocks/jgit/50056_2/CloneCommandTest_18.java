		try (
				Git git2 = Git.cloneRepository()
						.setDirectory(directory)
						.setCloneSubmodules(true)
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
			assertEquals(Constants.MASTER
			assertTrue(
					new File(db2.getWorkTree()
							path + File.separatorChar + file)
					.exists());

			Map<String
					git2.submoduleStatus().call();
			SubmoduleStatus pathStatus = statuses.get(path);
			assertNotNull(pathStatus);
			assertEquals(SubmoduleStatusType.INITIALIZED
					pathStatus.getType());
			assertEquals(commit
			assertEquals(commit

			try (SubmoduleWalk walk = SubmoduleWalk.forIndex(db2)) {
				assertTrue(walk.next());
				Repository clonedSub1 = walk.getRepository();
				addRepoToClose(clonedSub1);
				assertNotNull(clonedSub1);
				assertEquals(new File(db2.getWorkTree()
						clonedSub1.getWorkTree());
				assertEquals(
						new File(
								new File(db2.getDirectory()
								walk.getPath())
						clonedSub1.getDirectory());
			}
