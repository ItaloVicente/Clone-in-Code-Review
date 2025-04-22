		@SuppressWarnings("resource" /* java 7 */)
		final UnionInputStream u = new UnionInputStream();
		u.add(new ByteArrayInputStream(new byte[] { 1, 0, 2 }));
		u.add(new ByteArrayInputStream(new byte[] { 3 }));
		u.add(new ByteArrayInputStream(new byte[] { 4, 5 }));
		assertEquals(0, u.skip(0));
		assertEquals(3, u.skip(3));
		assertEquals(3, u.read());
		assertEquals(2, u.skip(5));
		assertEquals(0, u.skip(5));
		assertEquals(-1, u.read());
