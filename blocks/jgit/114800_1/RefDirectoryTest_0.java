	@Test
	public void testPreserveSymlinks() throws Exception {
		assumeTrue(!SystemReader.getInstance().isWindows());

		writePackedRefs(A.name() + " refs/heads/master\n");

		final FileRepository linkRepo = createBareRepository();
		final File orgObjects = new File(diskRepo.getDirectory()
		final File linkObjects = new File(linkRepo.getDirectory()
		final File orgRefs = new File(diskRepo.getDirectory()
		final File linkRefs = new File(linkRepo.getDirectory()
		final File orgPackedRefsFile = new File(diskRepo.getDirectory()
				"packed-refs");
		final File linkPackedRefsFile = new File(linkRepo.getDirectory()
				"packed-refs");

		linkObjects.delete();
		FileUtils.createSymLink(linkObjects

		linkRefs.delete();
		FileUtils.createSymLink(linkRefs
		assertTrue(Files.isSymbolicLink(linkRefs.toPath()));

		linkPackedRefsFile.delete();
		FileUtils.createSymLink(linkPackedRefsFile
				orgPackedRefsFile.getAbsolutePath());
		assertTrue(Files.isSymbolicLink(linkPackedRefsFile.toPath()));

		Map<String
		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(2
		assertEquals(Storage.LOOSE
		assertEquals(Storage.PACKED
		assertEquals(A.getId()

		write(new File(linkRepo.getDirectory()
				B.name() + "\n");

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(2
		assertEquals(Storage.LOOSE
		assertEquals(Storage.LOOSE
		assertEquals(B.getId()

		final RefDirectory rd = (RefDirectory) linkRepo.getRefDatabase();
		rd.pack(new ArrayList<>(rd.getRefs(RefDatabase.ALL).keySet()));

		assertTrue(Files.isSymbolicLink(linkRefs.toPath()));
		assertTrue(Files.isSymbolicLink(linkPackedRefsFile.toPath()));

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(2
		assertEquals(Storage.LOOSE
		assertEquals(Storage.PACKED
		assertEquals(B.getId()
	}

