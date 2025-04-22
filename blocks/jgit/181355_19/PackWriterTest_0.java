	@Test
	public void testTotalPackFilesScanWhenSearchForReuseTimeoutNotSet()
			throws Exception {
		FileRepository fileRepository = setUpRepoWithMultiplePackfiles();
		PackWriter mockedPackWriter = Mockito
				.spy(new PackWriter(config

		doNothing().when(mockedPackWriter).select(any()

		try (FileOutputStream packOS = new FileOutputStream(
				getPackFileToWrite(fileRepository
			mockedPackWriter.writePack(NullProgressMonitor.INSTANCE
					NullProgressMonitor.INSTANCE
		}

		long numberOfPackFiles = new GC(fileRepository)
				.getStatistics().numberOfPackFiles;
		int expectedSelectCalls =
				2 * (int) numberOfPackFiles +
						1;
		verify(mockedPackWriter
				any());
	}

	@Test
	public void testTotalPackFilesScanWhenSkippingSearchForReuseTimeoutCheck()
			throws Exception {
		FileRepository fileRepository = setUpRepoWithMultiplePackfiles();
		PackConfig packConfig = new PackConfig();
		packConfig.setSearchForReuseTimeout(Duration.ofSeconds(-1));
		PackWriter mockedPackWriter = Mockito.spy(
				new PackWriter(packConfig

		doNothing().when(mockedPackWriter).select(any()

		try (FileOutputStream packOS = new FileOutputStream(
				getPackFileToWrite(fileRepository
			mockedPackWriter.writePack(NullProgressMonitor.INSTANCE
					NullProgressMonitor.INSTANCE
		}

		long numberOfPackFiles = new GC(fileRepository)
				.getStatistics().numberOfPackFiles;
		int expectedSelectCalls =
				2 * (int) numberOfPackFiles +
						1;
		verify(mockedPackWriter
				any());
	}

	@Test
	public void testPartialPackFilesScanWhenDoingSearchForReuseTimeoutCheck()
			throws Exception {
		FileRepository fileRepository = setUpRepoWithMultiplePackfiles();
		PackConfig packConfig = new PackConfig();
		packConfig.setSearchForReuseTimeout(Duration.ofSeconds(-1));
		PackWriter mockedPackWriter = Mockito.spy(
				new PackWriter(packConfig
		mockedPackWriter.enableSearchForReuseTimeout();

		doNothing().when(mockedPackWriter).select(any()

		try (FileOutputStream packOS = new FileOutputStream(
				getPackFileToWrite(fileRepository
			mockedPackWriter.writePack(NullProgressMonitor.INSTANCE
					NullProgressMonitor.INSTANCE
		}

		verify(mockedPackWriter
				any());
	}

	private FileRepository setUpRepoWithMultiplePackfiles() throws Exception {
		FileRepository fileRepository = createWorkRepository();
		try (Git git = new Git(fileRepository)) {
			git.commit().setMessage("First commit").call();
			GC gc = new GC(fileRepository);
			gc.setPackExpireAgeMillis(Long.MAX_VALUE);
			gc.setExpireAgeMillis(Long.MAX_VALUE);
			gc.gc();
			git.commit().setMessage("Second commit").call();
			gc.gc();
			git.commit().setMessage("Third commit").call();
		}
		return fileRepository;
	}

	private PackFile getPackFileToWrite(FileRepository fileRepository
			PackWriter mockedPackWriter) throws IOException {
		File packdir = fileRepository.getObjectDatabase().getPackDirectory();
		PackFile packFile = new PackFile(packdir
				mockedPackWriter.computeName()

		Set<ObjectId> all = new HashSet<>();
		for (Ref r : fileRepository.getRefDatabase().getRefs())
			all.add(r.getObjectId());

		mockedPackWriter.preparePack(NullProgressMonitor.INSTANCE
				PackWriter.NONE);
		return packFile;
	}

