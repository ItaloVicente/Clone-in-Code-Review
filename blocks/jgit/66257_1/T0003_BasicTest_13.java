		try (final FileRepository db2 = new FileRepository(db.getDirectory())) {
			assertEquals(db.getDirectory()
			assertEquals(db.getObjectDatabase().getDirectory()
					.getObjectDatabase().getDirectory());
			assertNotSame(db.getConfig()
		}
