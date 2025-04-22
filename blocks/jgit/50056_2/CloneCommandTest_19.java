		try (
				Git sub1Git = Git.init()
						.setDirectory(submodule1)
						.call();
				Repository sub1 = sub1Git.getRepository();
				Git sub2Git = Git.init()
						.setDirectory(submodule2)
						.call();
				Repository sub2 = sub2Git.getRepository()) {
			String file = "file.txt";
			String path = "sub";

			write(new File(sub1.getWorkTree()
			sub1Git.add().addFilepattern(file).call();
			RevCommit commit = sub1Git.commit()
					.setMessage("create file")
					.call();
			assertNotNull(commit);

			write(new File(sub2.getWorkTree()
			sub2Git.add().addFilepattern(file).call();
			RevCommit sub2Head = sub2Git.commit()
					.setMessage("create file")
					.call();
			assertNotNull(sub2Head);

			RevCommit sub1Head;
			sub1Git.submoduleAdd()
					.setPath(path)
					.setURI(sub2.getDirectory().toURI().toString())
					.call()
					.close();
			sub1Head = sub1Git.commit()
					.setAll(true)
					.setMessage("Adding submodule")
					.call();
			assertNotNull(sub1Head);

			git.submoduleAdd()
					.setPath(path)
					.setURI(sub1.getDirectory().toURI().toString())
					.call()
					.close();
			assertNotNull(git.commit()
					.setAll(true)
					.setMessage("Adding submodule")
					.call());

			File directory = createTempDirectory("testCloneRepositoryWithNestedSubmodules");
			try (
					Git git2 = Git.cloneRepository()
							.setDirectory(directory)
							.setCloneSubmodules(true)
							.setURI(db.getDirectory().toURI().toString())
							.call();
					Repository db2 = git2.getRepository()) {
				assertEquals(Constants.MASTER
				assertTrue(
						new File(db2.getWorkTree()
								path + File.separatorChar + file)
						.exists());
				assertTrue(
						new File(db2.getWorkTree()
								path + File.separatorChar
										+ path + File.separatorChar
										+ file)
						.exists());

				Map<String
						git2.submoduleStatus().call();
				SubmoduleStatus pathStatus = statuses.get(path);
				assertNotNull(pathStatus);
				assertEquals(SubmoduleStatusType.INITIALIZED
				assertEquals(sub1Head
				assertEquals(sub1Head

				SubmoduleWalk walk = SubmoduleWalk.forIndex(db2);
				assertTrue(walk.next());
				try (Repository clonedSub1 = walk.getRepository()) {
					assertNotNull(clonedSub1);
					assertEquals(
							new File(db2.getWorkTree()
							clonedSub1.getWorkTree());
					assertEquals(
							new File(
									new File(db2.getDirectory()
									walk.getPath())
							clonedSub1.getDirectory());
					statuses = new Git(clonedSub1).submoduleStatus().call();
				}
				pathStatus = statuses.get(path);
				assertNotNull(pathStatus);
				assertEquals(SubmoduleStatusType.INITIALIZED
				assertEquals(sub2Head
				assertEquals(sub2Head
				assertFalse(walk.next());
			}
		}
