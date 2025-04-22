		final UnionInputStream u = new UnionInputStream();
		assertTrue(u.isEmpty());
		assertEquals(-1, u.read());
		assertEquals(-1, u.read(new byte[1], 0, 1));
		assertEquals(0, u.available());
		assertEquals(0, u.skip(1));
		u.close();
