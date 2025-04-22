		assertTrue(diskRepo.getDirectoryChild(REFS).isDirectory());
		assertTrue(diskRepo.getDirectoryChild(LOGS).isDirectory());
		assertTrue(diskRepo.getDirectoryChild("logs/refs").isDirectory());
		assertFalse(diskRepo.getDirectoryChild(PACKED_REFS).exists());

		assertTrue(diskRepo.getDirectoryChild("refs/heads").isDirectory());
		assertTrue(diskRepo.getDirectoryChild("refs/tags").isDirectory());
		assertEquals(2
				diskRepo.getDirectoryChild(REFS).list().length);
		assertEquals(0
		assertEquals(0

		assertTrue(diskRepo.getDirectoryChild("logs/refs/heads").isDirectory());
		assertFalse(diskRepo.getDirectoryChild("logs/HEAD").exists());
		assertEquals(0
				diskRepo.getDirectoryChild("logs/refs/heads").list().length);

		assertEquals("ref: refs/heads/master\n"
				read(diskRepo.getDirectoryChild(HEAD)));
