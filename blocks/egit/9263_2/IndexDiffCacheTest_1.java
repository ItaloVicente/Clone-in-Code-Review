	@Test
	public void testAddIgnoredFolder() throws Exception {
		testRepository.connect(project.project);
		project.createFile(".gitignore", "ignore\n".getBytes("UTF-8"));
		project.createFolder("ignore");
		project.createFile("ignore/file.txt", new byte[] {});
		project.createFolder("sub");
		testRepository.addToIndex(project.project);
		testRepository.createInitialCommit("testAddFileInIgnoredFolder\n\nfirst commit\n");
		prepareCacheEntry();

		IndexDiffData data1 = waitForListenerCalled();
		assertThat(data1.getIgnoredNotInIndex(), hasItem("Project-1/ignore"));

		project.createFolder("sub/ignore");

		IndexDiffData data2 = waitForListenerCalled();
		assertThat(data2.getIgnoredNotInIndex(), hasItem("Project-1/ignore"));
		assertThat(data2.getIgnoredNotInIndex(), hasItem("Project-1/sub/ignore"));

		project.createFile("sub/ignorenot", new byte[] {});

		IndexDiffData data3 = waitForListenerCalled();
		assertThat(data3.getUntracked(), hasItem("Project-1/sub/ignorenot"));
		assertThat(data3.getIgnoredNotInIndex(), hasItem("Project-1/ignore"));
		assertThat(data3.getIgnoredNotInIndex(), hasItem("Project-1/sub/ignore"));
	}

	@Test
	public void testRemoveIgnoredFile() throws Exception {
		testRepository.connect(project.project);
		project.createFile(".gitignore", "ignore\n".getBytes("UTF-8"));
		project.createFolder("sub");
		IFile file = project.createFile("sub/ignore", new byte[] {});
		testRepository.addToIndex(project.project);
		testRepository.createInitialCommit("testRemoveIgnoredFile\n\nfirst commit\n");
		prepareCacheEntry();

		IndexDiffData data1 = waitForListenerCalled();
		assertThat(data1.getIgnoredNotInIndex(), hasItem("Project-1/sub/ignore"));

		project.createFile("sub/ignorenot", new byte[] {});

		IndexDiffData data2 = waitForListenerCalled();
		assertThat(data2.getIgnoredNotInIndex(), hasItem("Project-1/sub/ignore"));

		file.delete(false, null);

		IndexDiffData data3 = waitForListenerCalled();
		assertThat(data3.getIgnoredNotInIndex(), not(hasItem("Project-1/sub/ignore")));
	}

