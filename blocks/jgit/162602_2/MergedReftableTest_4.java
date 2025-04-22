	@Test
	public void nonOverlappedUpdateIndices() throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter(buf)
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(2)
				.begin();
		writer.writeRef(ref("refs/heads/a"
		writer.writeRef(ref("refs/heads/b"
		writer.finish();
		byte[] base = buf.toByteArray();

		buf = new ByteArrayOutputStream();
		writer = new ReftableWriter(buf)
				.setMinUpdateIndex(3)
				.setMaxUpdateIndex(4)
				.begin();
		writer.writeRef(ref("refs/heads/a"
		writer.writeRef(ref("refs/heads/b"
		writer.finish();
		byte[] delta = buf.toByteArray();

		MergedReftable mr = merge(base
		assertEquals(1
		assertEquals(4

		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			assertEquals("refs/heads/a"
			assertEquals(id(10)
			assertEquals(3

			assertTrue(rc.next());
			assertEquals("refs/heads/b"
			assertEquals(id(20)
			assertEquals(4
		}
	}

	@Test
	public void overlappedUpdateIndices() throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter(buf)
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(3)
				.begin();
		writer.writeRef(ref("refs/heads/a"
		writer.writeRef(ref("refs/heads/b"
		writer.finish();
		byte[] base = buf.toByteArray();

		buf = new ByteArrayOutputStream();
		writer = new ReftableWriter(buf)
				.setMinUpdateIndex(2)
				.setMaxUpdateIndex(4)
				.begin();
		writer.writeRef(ref("refs/heads/a"
		writer.writeRef(ref("refs/heads/b"
		writer.finish();
		byte[] delta = buf.toByteArray();

		MergedReftable mr = merge(base
		assertEquals(1
		assertEquals(4

		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			assertEquals("refs/heads/a"
			assertEquals(id(10)
			assertEquals(2

			assertTrue(rc.next());
			assertEquals("refs/heads/b"
			assertEquals(id(20)
			assertEquals(4
		}
	}

	@Test
	public void enclosedUpdateIndices() throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter(buf)
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(4)
				.begin();
		writer.writeRef(ref("refs/heads/a"
		writer.writeRef(ref("refs/heads/b"
		writer.finish();
		byte[] base = buf.toByteArray();

		buf = new ByteArrayOutputStream();
		writer = new ReftableWriter(buf)
				.setMinUpdateIndex(2)
				.setMaxUpdateIndex(3)
				.begin();
		writer.writeRef(ref("refs/heads/a"
		writer.writeRef(ref("refs/heads/b"
		writer.finish();
		byte[] delta = buf.toByteArray();

		MergedReftable mr = merge(base
		assertEquals(1
		assertEquals(4

		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			assertEquals("refs/heads/a"
			assertEquals(id(10)
			assertEquals(2

			assertTrue(rc.next());
			assertEquals("refs/heads/b"
			assertEquals(id(20)
			assertEquals(4
		}
	}

