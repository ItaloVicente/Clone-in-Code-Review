		@SuppressWarnings("resource" /* java 7 */)
		final UnionInputStream u = new UnionInputStream(
				new ByteArrayInputStream(new byte[]{1,2,3}),
				errorReadStream);
		byte buf[] = new byte[10];
		assertEquals(3, u.read(buf, 0, 10));
		assertTrue(Arrays.equals(new byte[] {1,2,3}, slice(buf, 3)));
		try {
			u.read(buf, 0, 1);
			fail("Expected exception from errorReadStream");
		} catch (IOException e) {
			assertEquals("Expected", e.getMessage());
