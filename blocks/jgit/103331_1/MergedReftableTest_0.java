	@Test
	public void missedUpdate() throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(3)
				.begin(buf);
		writer.writeRef(ref("refs/heads/a"
		writer.writeRef(ref("refs/heads/c"
		writer.finish();
		byte[] base = buf.toByteArray();

		byte[] delta = write(Arrays.asList(
				ref("refs/heads/b"
				ref("refs/heads/c"
				2);
		MergedReftable mr = merge(base
		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			assertEquals("refs/heads/a"
			assertEquals(id(1)
			assertEquals(1

			assertTrue(rc.next());
			assertEquals("refs/heads/b"
			assertEquals(id(2)
			assertEquals(2

			assertTrue(rc.next());
			assertEquals("refs/heads/c"
			assertEquals(id(3)
			assertEquals(3
		}
	}

