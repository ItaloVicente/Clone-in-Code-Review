	@Test
	public void testAddFileFromUntrackedFolder() throws Exception {
		testRepository.connect(project.project);
		testRepository.addToIndex(project.project);
		testRepository.createInitialCommit("testAddFileFromUntrackedFolder\n\nfirst commit\n");
		prepareCacheEntry();

		project.createFolder("folder");
		project.createFolder("folder/a");
		project.createFolder("folder/b");
		IFile fileA = project.createFile("folder/a/file", new byte[] {});
		project.createFile("folder/b/file", new byte[] {});

		IndexDiffData data1 = waitForListenerCalled();
		assertThat(data1.getUntrackedFolders(), hasItem("Project-1/folder/"));

		testRepository.track(fileA.getLocation().toFile());

		IndexDiffData data2 = waitForListenerCalled();
		assertThat(data2.getAdded(), hasItem("Project-1/folder/a/file"));
		assertThat(data2.getUntrackedFolders(), not(hasItem("Project-1/folder/")));
		assertThat(data2.getUntrackedFolders(), not(hasItem("Project-1/folder/a")));
		assertThat(data2.getUntrackedFolders(), hasItem("Project-1/folder/b/"));
	}

	private void prepareCacheEntry() {
		IndexDiffCache indexDiffCache = Activator.getDefault()
				.getIndexDiffCache();
		IndexDiffCacheEntry cacheEntry = indexDiffCache
				.getIndexDiffCacheEntry(repository);
		listenerCalled = new AtomicBoolean(false);
		indexDiffDataResult = new AtomicReference<IndexDiffData>(
				null);
		cacheEntry.addIndexDiffChangedListener(new IndexDiffChangedListener() {
			public void indexDiffChanged(Repository repo,
					IndexDiffData indexDiffData) {
				listenerCalled.set(true);
				indexDiffDataResult.set(indexDiffData);
			}
		});
	}

	private IndexDiffData waitForListenerCalled() throws InterruptedException {
