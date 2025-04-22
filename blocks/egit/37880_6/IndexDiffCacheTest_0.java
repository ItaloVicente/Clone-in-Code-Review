	@Test
	public void testAddAndRemoveGitIgnoreFileToIgnoredDir() throws Exception {
		testRepository.connect(project.project);
		project.createFile(".gitignore", "ignore\n".getBytes("UTF-8"));
		project.createFolder("sub");
		project.createFile("sub/ignore", new byte[] {});
		testRepository.addToIndex(project.project);
		testRepository
				.createInitialCommit("testRemoveIgnoredFile\n\nfirst commit\n");
		prepareCacheEntry();

		IndexDiffData data1 = waitForListenerCalled();
		assertThat(data1.getIgnoredNotInIndex(),
				hasItem("Project-1/sub/ignore"));

		project.createFile("sub/ignored", "Ignored".getBytes());

		project.createFile("sub/.gitignore", "ignored\n".getBytes());

		IndexDiffData data2 = waitForListenerCalled();
		assertThat(data2.getIgnoredNotInIndex(),
				hasItem("Project-1/sub/ignored"));

		project.getProject().getFile("sub/.gitignore").delete(true, null);

		IndexDiffData data3 = waitForListenerCalled();
		assertThat(data3.getUntracked(), hasItem("Project-1/sub/ignored"));
	}

	@Test
	public void testAddAndRemoveFileToIgnoredDir() throws Exception {
		testRepository.connect(project.project);
		project.createFile(".gitignore", "sub\n".getBytes("UTF-8"));
		project.createFolder("sub");
		project.createFile("sub/ignore", new byte[] {});
		testRepository.addToIndex(project.project);
		testRepository
				.createInitialCommit("testRemoveIgnoredFile\n\nfirst commit\n");
		prepareCacheEntry();

		IndexDiffData data1 = waitForListenerCalled();
		assertThat(data1.getIgnoredNotInIndex(), hasItem("Project-1/sub"));

		project.createFile("sub/ignored", "Ignored".getBytes());
		waitForListenerNotCalled();

		project.getProject().getFile("sub/ignored").delete(true, null);
		waitForListenerNotCalled();
	}

	@Test
	public void testModifyFileInIgnoredDir() throws Exception {
		testRepository.connect(project.project);
		project.createFile(".gitignore", "ignore\n".getBytes("UTF-8"));
		project.createFolder("sub");
		project.createFile("sub/ignore", new byte[] {});
		testRepository.addToIndex(project.project);
		testRepository
				.createInitialCommit("testRemoveIgnoredFile\n\nfirst commit\n");
		prepareCacheEntry();

		IndexDiffData data1 = waitForListenerCalled();
		assertThat(data1.getIgnoredNotInIndex(),
				hasItem("Project-1/sub/ignore"));

		IFile file = project.getProject().getFile("sub/ignore");
		FileOutputStream str = new FileOutputStream(file.getLocation().toFile());
		try {
			str.write("other contents".getBytes());
		} finally {
			str.close();
		}

		waitForListenerNotCalled();
	}

	private IndexDiffCacheEntry prepareCacheEntry() {
