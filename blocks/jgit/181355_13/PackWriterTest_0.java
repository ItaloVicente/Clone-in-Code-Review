	@Test
	public void testTotalPackFilesScanWhenSearchForReuseMaxTimeSecNotSet() throws Exception {
		FileRepository fileRepository = setUpRepoWithMultiplePackfiles();
		PackWriter mockedPackWriter = Mockito.spy(new PackWriter(config

		doNothing().when(mockedPackWriter).select(any()

		try (FileOutputStream packOS = new FileOutputStream(getPackFileToWrite(fileRepository
			mockedPackWriter.writePack(NullProgressMonitor.INSTANCE
		}

		long numberOfPackFiles = new GC(fileRepository).getStatistics().numberOfPackFiles;
		verify(mockedPackWriter
	}

	@Test
	public void testTotalPackFilesScanWhenLookingForBestPackRepresentation() throws Exception {
		FileRepository fileRepository = setUpRepoWithMultiplePackfiles();
		PackConfig packConfig = new PackConfig();
		packConfig.setSearchForReuseMaxTimeSec(-1);
		PackWriter mockedPackWriter = Mockito.spy(new PackWriter(packConfig
		mockedPackWriter.setFindBestPackRepresentation(true);

		doNothing().when(mockedPackWriter).select(any()

		try (FileOutputStream packOS = new FileOutputStream(getPackFileToWrite(fileRepository
			mockedPackWriter.writePack(NullProgressMonitor.INSTANCE
		}

		long numberOfPackFiles = new GC(fileRepository).getStatistics().numberOfPackFiles;
		verify(mockedPackWriter
	}

	@Test
	public void testPartialPackFilesScanWhenSearchForReuseMaxTimeSecNotSet() throws Exception
		FileRepository fileRepository = setUpRepoWithMultiplePackfiles();
		PackConfig packConfig = new PackConfig();
		packConfig.setSearchForReuseMaxTimeSec(-1);
		PackWriter mockedPackWriter = Mockito.spy(new PackWriter(packConfig

		doNothing().when(mockedPackWriter).select(any()

		try (FileOutputStream packOS = new FileOutputStream(getPackFileToWrite(fileRepository
			mockedPackWriter.writePack(NullProgressMonitor.INSTANCE
		}

		verify(mockedPackWriter
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
		File packdir = fileRepository.getObjectDatabase().getPackDirectory();
		PackFile packFile = new PackFile(packdir
				PackExt.PACK);

		Set <ObjectId> all = new HashSet <>();
		for (Ref r : fileRepository.getRefDatabase().getRefs())
			all.add(r.getObjectId());

		mockedPackWriter.preparePack(NullProgressMonitor.INSTANCE
		return packFile;
	}

