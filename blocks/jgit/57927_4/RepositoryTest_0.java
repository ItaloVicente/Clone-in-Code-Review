		assertFalse(db.isClosed());
	}

	@Test
	public void testOpenPackFileOnClosedRepo() throws Exception {
		RevCommit commit1 = commitFile("a"

		new GC(db).repack();
		ObjectDirectory odb = db.getObjectDatabase();
		assertEquals(1
		assertClosedFileDescriptor(odb.getPacks().iterator().next());
		assertEquals(1

		ObjectReader reader = db.newObjectReader();
		try (RevWalk rw = new RevWalk(reader)) {
			rw.parseAny(commit1.getId());
		}
		assertOpenFileDescriptor(odb.getPacks().iterator().next());
		assertEquals(1

		db.close();
		assertEquals(0
		assertClosedFileDescriptor(odb.getPacks().iterator().next());

		reader = db.newObjectReader();
		try (RevWalk rw = new RevWalk(reader)) {
			rw.parseAny(commit1.getId());
		}

		assertEquals(1
		assertOpenFileDescriptor(odb.getPacks().iterator().next());

		db.close();

		assertClosedFileDescriptor(odb.getPacks().iterator().next());
	}

	private void assertOpenFileDescriptor(PackFile openPack)
			throws NoSuchFieldException
		Field field = PackFile.class.getDeclaredField("fd");
		field.setAccessible(true);
		assertNotNull(field.get(openPack));
	}

	private void assertClosedFileDescriptor(PackFile openPack)
			throws NoSuchFieldException
		Field field = PackFile.class.getDeclaredField("fd");
		field.setAccessible(true);
		assertNull(field.get(openPack));
