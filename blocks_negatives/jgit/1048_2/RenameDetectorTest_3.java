		DiffEntry readme = entries.get(0);
		assertNotNull(readme);
		assertTrue(bar.equals(readme.newId.toObjectId()));
		assertTrue(bar.equals(readme.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE, readme.newMode);
		assertEquals(FileMode.REGULAR_FILE, readme.oldMode);
		assertEquals(ChangeType.RENAME, readme.changeType);
		assertEquals("README", readme.newName);
		assertEquals("REEDME", readme.oldName);

		DiffEntry somefile = entries.get(1);
		assertNotNull(somefile);
		assertTrue(foo.equals(somefile.newId.toObjectId()));
		assertTrue(foo.equals(somefile.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE, somefile.newMode);
		assertEquals(FileMode.REGULAR_FILE, somefile.oldMode);
		assertEquals(ChangeType.RENAME, somefile.changeType);
		assertEquals("some/file.c", somefile.newName);
		assertEquals("some/other_file.c", somefile.oldName);
