		DiffEntry rename = entries.get(0);
		assertNotNull(rename);
		assertTrue(foo.equals(rename.newId.toObjectId()));
		assertTrue(foo.equals(rename.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE, rename.newMode);
		assertEquals(FileMode.REGULAR_FILE, rename.oldMode);
		assertEquals(ChangeType.RENAME, rename.changeType);
		assertEquals("some/file.c", rename.newName);
		assertEquals("some/other_file.c", rename.oldName);

		DiffEntry modify = entries.get(1);
		assertEquals(c, modify);
