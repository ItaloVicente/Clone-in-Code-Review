		setupCacheToTestOrder(config
		cache.get("fs1");
		cache.get("fs2");
		assertFalse(cache.memoizedSuppliers.containsKey("fs3"));

		cache.get("fs1");
		cache.get("fs2");
		cache.get("fs3");
		setupCacheToTestOrder(config
		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));
	}

	@Test
	public void removeEldestEntryTest() {

		JGitFileSystemProviderConfiguration config = setupConfigMock();

		setupCacheToTestOrder(config
		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));

		cache = new JGitFileSystemsCache(config);

		JGitFileSystem fs1 = mock(JGitFileSystem.class);
		when(fs1.hasBeenInUse()).thenReturn(true);
		Supplier<JGitFileSystem> fsSupplier1 = getSupplierSpy(fs1);
		cache.addSupplier("fs1"

		JGitFileSystem fs2 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fsSupplier2 = getSupplierSpy(fs2);
		when(fs2.hasBeenInUse()).thenReturn(false);
		cache.addSupplier("fs2"

		JGitFileSystem fs3 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fsSupplier = getSupplierSpy(fs3);
		when(fs3.hasBeenInUse()).thenReturn(false);
		cache.addSupplier("fs3"

		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs3"));
		assertFalse(cache.memoizedSuppliers.containsKey("fs2"));
	}

	@Test
	public void removeEldestEntryTestAllOpen() {

		JGitFileSystemProviderConfiguration config = setupConfigMock();

		cache = new JGitFileSystemsCache(config);

		JGitFileSystem fs1 = mock(JGitFileSystem.class);
		when(fs1.hasBeenInUse()).thenReturn(true);
		Supplier<JGitFileSystem> fsSupplier1 = getSupplierSpy(fs1);
		cache.addSupplier("fs1"

		JGitFileSystem fs2 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs2Supplier = getSupplierSpy(fs2);
		when(fs2.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs2"

		JGitFileSystem fs3 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs3Supplier = getSupplierSpy(fs3);
		when(fs3.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs3"

		JGitFileSystem fs4 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs4Supplier = getSupplierSpy(fs4);
		when(fs4.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs4"

		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs3"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs2"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs4"));

		when(fs1.hasBeenInUse()).thenReturn(false);
		when(fs2.hasBeenInUse()).thenReturn(false);
		when(fs4.hasBeenInUse()).thenReturn(false);

		JGitFileSystem fs5 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs5Supplier = getSupplierSpy(fs5);
		when(fs5.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs5"

		assertTrue(cache.memoizedSuppliers.containsKey("fs3"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs5"));
		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));
		assertFalse(cache.memoizedSuppliers.containsKey("fs2"));
	}

	@Test
	public void fsInUseAreAlwaysOnTheCache() throws IOException

		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {
			@Override
			public int getJgitFileSystemsInstancesCache() {
				return 2;
			}
		};

		cache = new JGitFileSystemsCache(config);

		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit();

		final JGitFileSystemImpl fs1 = new JGitFileSystemImpl(fsProvider
				new JGitFileSystemLock(git
				CredentialsProvider.getDefault()

		Supplier<JGitFileSystem> fs1Supplier = getSupplierSpy(fs1);
		cache.addSupplier("fs1"

		fs1.lock();
		fs1.lock();
		fs1.unlock();
		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));

		JGitFileSystem fs2 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs2Supplier = getSupplierSpy(fs2);
		when(fs2.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs2"

		JGitFileSystem fs3 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs3Supplier = getSupplierSpy(fs3);
		when(fs3.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs5"

		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
	}

	private void setupCacheToTestOrder(JGitFileSystemProviderConfiguration config
		cache = new JGitFileSystemsCache(config);

		Arrays.stream(fsNames).forEach(fsName -> {
			JGitFileSystem fs = mock(JGitFileSystem.class);
			Supplier<JGitFileSystem> fsSupplier = getSupplierSpy(fs);
			cache.addSupplier(fsName
		});
	}

	private Supplier<JGitFileSystem> getSupplierSpy(final JGitFileSystem fs1) {
		return spy(new Supplier<JGitFileSystem>() {
			@Override
			public JGitFileSystem get() {
				return fs1;
			}
		});
	}

	private JGitFileSystemProviderConfiguration setupConfigMock() {
		return new JGitFileSystemProviderConfiguration() {
			@Override
			public int getJgitFileSystemsInstancesCache() {
				return 2;
			}

			@Override
			public int getJgitRemoveEldestEntryIterations() {
				return 10;
			}

			@Override
			public int getJgitCacheOverflowCleanupSize() {
				return 10;
			}
		};
	}
