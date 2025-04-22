				new ByteArrayInputStream(new byte[] { 4, 5 }));

		final byte[] r = new byte[5];
		assertEquals(3, u.read(r, 0, 5));
		assertTrue(Arrays.equals(new byte[] { 1, 0, 2, }, slice(r, 3)));
		assertEquals(1, u.read(r, 0, 5));
		assertEquals(3, r[0]);
		assertEquals(2, u.read(r, 0, 5));
		assertTrue(Arrays.equals(new byte[] { 4, 5, }, slice(r, 2)));
		assertEquals(-1, u.read(r, 0, 5));
