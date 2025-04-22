	@Test
	public void testSimulatePackfileReplacement() throws Exception {
		waitNextSec(f1);
		waitNextSec(f1);
		FileTime timestamp = Files.getLastModifiedTime(f1.toPath());
		FileSnapshot save = FileSnapshot.save(f1);
		Files.move(f2.toPath()
				StandardCopyOption.REPLACE_EXISTING
				StandardCopyOption.ATOMIC_MOVE);
		Files.setLastModifiedTime(f1.toPath()
		assertTrue(save.isModified(f1));
	}

