	private final FileTreeIterator.FileModeStrategy NO_GITLINKS_STRATEGY =
			new FileTreeIterator.FileModeStrategy() {
				@Override
				public FileMode getMode(File f
					if (attributes.isSymbolicLink()) {
						return FileMode.SYMLINK;
					} else if (attributes.isDirectory()) {
						return FileMode.TREE;
					} else if (attributes.isExecutable()) {
						return FileMode.EXECUTABLE_FILE;
					} else {
						return FileMode.REGULAR_FILE;
					}
				}
			};

	private Repository createNestedRepo() throws IOException {
		File gitdir = createUniqueTestGitDir(false);
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setGitDir(gitdir);
		Repository nestedRepo = builder.build();
		nestedRepo.create();

		JGitTestUtil.writeTrashFile(nestedRepo

		File nestedRepoPath = new File(nestedRepo.getWorkTree()
		FileRepositoryBuilder nestedBuilder = new FileRepositoryBuilder();
		nestedBuilder.setWorkTree(nestedRepoPath);
		nestedBuilder.build().create();

		JGitTestUtil.writeTrashFile(nestedRepo
				"content b");

		return nestedRepo;
	}

	@Test
	public void testCustomFileModeStrategy() throws Exception {
		Repository nestedRepo = createNestedRepo();

		Git git = new Git(nestedRepo);
		WorkingTreeIterator customIterator =
				new FileTreeIterator(nestedRepo
		git.add().setWorkingTreeIterator(customIterator)
				.addFilepattern(".").call();
		assertEquals(
				"[sub/a.txt
				"[sub/nested/b.txt
				indexState(nestedRepo

	}

	@Test
	public void testCustomFileModeStrategyFromParentIterator() throws Exception {
		Repository nestedRepo = createNestedRepo();

		Git git = new Git(nestedRepo);

		FileTreeIterator customIterator =
				new FileTreeIterator(nestedRepo
		File r = new File(nestedRepo.getWorkTree()

		FileTreeIterator childIterator =
				new FileTreeIterator(customIterator
		git.add().setWorkingTreeIterator(childIterator).addFilepattern(".").call();
		assertEquals(
				"[sub/a.txt
				"[sub/nested/b.txt
				indexState(nestedRepo
	}


