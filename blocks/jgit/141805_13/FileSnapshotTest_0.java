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

