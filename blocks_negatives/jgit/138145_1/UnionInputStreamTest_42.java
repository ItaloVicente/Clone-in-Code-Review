		assertFalse(u.isEmpty());
		assertEquals(3, u.available());
		assertEquals(1, u.read());
		assertEquals(0, u.read());
		assertEquals(2, u.read());
		assertEquals(0, u.available());
