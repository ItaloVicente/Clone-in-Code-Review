	@Test
	public void testFileKeyChanged() throws Exception {
		File f = createFile("file");
		FileTime timestamp = Files.getLastModifiedTime(f.toPath());
		FileSnapshot save = FileSnapshot.save(f);
		FileUtils.delete(f);
		f = createFile("file");
		Files.setLastModifiedTime(f.toPath()
		assertTrue(save.isModified(f));
	}


	@Test
	public void testSimulatePackfileReplacement() throws Exception {
		waitNextSec(f1);
		FileTime timestamp = Files.getLastModifiedTime(f1.toPath());
		FileSnapshot save = FileSnapshot.save(f1);
		Files.move(f2.toPath()
				StandardCopyOption.REPLACE_EXISTING
				StandardCopyOption.ATOMIC_MOVE);
		Files.setLastModifiedTime(f1.toPath()
		assertTrue(save.isModified(f1));
	}

