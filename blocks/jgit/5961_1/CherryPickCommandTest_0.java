	@Test
	public void testCherryPickOverExecutableChangeOnNonExectuableFileSystem()
			throws Exception {
		Git git = new Git(db);
		File file = writeTrashFile("test.txt"
		assertNotNull(git.add().addFilepattern("test.txt").call());
		assertNotNull(git.commit().setMessage("commit1").call());

		assertNotNull(git.checkout().setCreateBranch(true).setName("a").call());

		writeTrashFile("test.txt"
		assertNotNull(git.add().addFilepattern("test.txt").call());
		RevCommit commit2 = git.commit().setMessage("commit2").call();
		assertNotNull(commit2);

		assertNotNull(git.checkout().setName(Constants.MASTER).call());

		DirCache cache = db.lockDirCache();
		cache.getEntry("test.txt").setFileMode(FileMode.EXECUTABLE_FILE);
		cache.write();
		assertTrue(cache.commit());
		cache.unlock();
		db.getFS().setExecute(file

		assertNotNull(git.commit().setMessage("commit3").call());

		final FS baseFs = db.getFS();
		FS nonExecutableFs;
		if (!baseFs.supportsExecute())
			nonExecutableFs = baseFs;
		else
			nonExecutableFs = new FS() {

				public boolean supportsExecute() {
					return false;
				}

				public boolean setExecute(File f
					return false;
				}

				public ProcessBuilder runInShell(String cmd
					return baseFs.runInShell(cmd
				}

				public boolean retryFailedLockFileCommit() {
					return baseFs.retryFailedLockFileCommit();
				}

				public FS newInstance() {
					return this;
				}

				protected File discoverGitPrefix() {
					return null;
				}

				public boolean canExecute(File f) {
					return false;
				}
			};

		git = Git.wrap(new FileRepositoryBuilder().setGitDir(db.getDirectory())
				.setFS(nonExecutableFs).setMustExist(true).build());
		CherryPickResult result = git.cherryPick().include(commit2).call();
		assertNotNull(result);
		assertEquals(CherryPickStatus.OK
	}

