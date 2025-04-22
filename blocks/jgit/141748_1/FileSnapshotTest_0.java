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

