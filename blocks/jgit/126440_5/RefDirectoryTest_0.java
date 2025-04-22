		assertTrue(diskRepo.getDirectoryChild("refs/heads").isDirectory());
		assertTrue(diskRepo.getDirectoryChild("refs/tags").isDirectory());
		assertEquals(2
				diskRepo.getDirectoryChild(REFS).list().length);
		assertEquals(0
		assertEquals(0
