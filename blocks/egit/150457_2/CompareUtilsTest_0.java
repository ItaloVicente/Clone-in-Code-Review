	@Test
	public void testIndexEditExecutable() throws Exception {
		assumeTrue(repository.getFS().supportsExecute());
		IFile testFile = touch("a");
		File rawFile = new File(testFile.getLocation().toOSString());
		repository.getFS().setExecute(rawFile, true);
		testFile.refreshLocal(IResource.DEPTH_ZERO, null);
		stage(testFile);
		assertEquals("Executable bit should be set", FileMode.EXECUTABLE_FILE,
				getIndexEntryMode(FILE1_PATH));
		ITypedElement element = CompareUtils.getIndexTypedElement(testFile);
		assert (element instanceof EditableRevision);
		EditableRevision revision = (EditableRevision) element;
		try (InputStream in = revision.getContents()) {
			assertEquals("a", get(in));
		}
		revision.setContent("xx".getBytes(StandardCharsets.UTF_8));
		assertEquals("Executable bit should be set", FileMode.EXECUTABLE_FILE,
				getIndexEntryMode(FILE1_PATH));
	}

	private FileMode getIndexEntryMode(String path) throws Exception {
		DirCache dc = repository.readDirCache();
		try (TreeWalk w = new TreeWalk(repository)) {
			w.addTree(new DirCacheIterator(dc));
			w.setFilter(PathFilterGroup.createFromStrings(path));
			w.setRecursive(true);
			assertTrue(path + "not in index", w.next());
			return w.getFileMode();
		}
	}

