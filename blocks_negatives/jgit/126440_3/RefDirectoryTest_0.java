		assertTrue(new File(d, Constants.REFS).isDirectory());
		assertTrue(new File(d, Constants.LOGS).isDirectory());
		assertTrue(new File(d, "logs/refs").isDirectory());
		assertFalse(new File(d, "packed-refs").exists());

		assertTrue(new File(d, "refs/heads").isDirectory());
		assertTrue(new File(d, "refs/tags").isDirectory());
		assertEquals(2, new File(d, Constants.REFS).list().length);
		assertEquals(0, new File(d, "refs/heads").list().length);
		assertEquals(0, new File(d, "refs/tags").list().length);

		assertTrue(new File(d, "logs/refs/heads").isDirectory());
		assertFalse(new File(d, "logs/HEAD").exists());
		assertEquals(0, new File(d, "logs/refs/heads").list().length);

		assertEquals("ref: refs/heads/master\n", read(new File(d, HEAD)));
