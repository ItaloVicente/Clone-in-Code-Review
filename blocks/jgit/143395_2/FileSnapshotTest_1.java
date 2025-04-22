	@Test
	public void testSimulatePackfileReplacement() throws Exception {
		Assume.assumeFalse(SystemReader.getInstance().isWindows());
		waitNextSec(f2);
		waitNextSec(f2);
		FileTime timestamp = Files.getLastModifiedTime(f1.toPath());
		FileSnapshot save = FileSnapshot.save(f1);
		Files.move(f2.toPath()
				StandardCopyOption.REPLACE_EXISTING
				StandardCopyOption.ATOMIC_MOVE);
		Files.setLastModifiedTime(f1.toPath()
		assertTrue(save.isModified(f1));
		assertTrue("unexpected change of fileKey"
		assertFalse("unexpected size change"
		assertFalse("unexpected lastModified change"
				save.wasLastModifiedChanged());
		assertFalse("lastModified was unexpectedly racily clean"
				save.wasLastModifiedRacilyClean());
	}

	@Test
	public void testFileSizeChanged() throws Exception {
		File f = createFile("file");
		FileTime timestamp = Files.getLastModifiedTime(f.toPath());
		FileSnapshot save = FileSnapshot.save(f);
		append(f
		Files.setLastModifiedTime(f.toPath()
		assertTrue(save.isModified(f));
		assertTrue(save.wasSizeChanged());
	}

