	@Test
	public void testGetShortRef() throws IOException {
		Ref ref = db.getRef("master");
		assertEquals("refs/heads/master"
		assertEquals(db.resolve("refs/heads/master")
	}

	@Test
	public void testGetShortExactRef() throws IOException {
		assertNull(db.getRefDatabase().exactRef("master"));

		Ref ref = db.getRefDatabase().exactRef("HEAD");
		assertEquals("HEAD"
		assertEquals("refs/heads/master"
		assertEquals(db.resolve("refs/heads/master")
	}

	@Test
	public void testRefsUnderRefs() throws IOException {
		ObjectId masterId = db.resolve("refs/heads/master");
		writeNewRef("refs/heads/refs/foo/bar"

		assertNull(db.getRefDatabase().exactRef("refs/foo/bar"));

		Ref ref = db.getRef("refs/foo/bar");
		assertEquals("refs/heads/refs/foo/bar"
		assertEquals(db.resolve("refs/heads/master")
	}

	@Test
	public void testAmbiguousRefsUnderRefs() throws IOException {
		ObjectId masterId = db.resolve("refs/heads/master");
		writeNewRef("refs/foo/bar"
		writeNewRef("refs/heads/refs/foo/bar"

		Ref exactRef = db.getRefDatabase().exactRef("refs/foo/bar");
		assertEquals("refs/foo/bar"
		assertEquals(masterId

		Ref ref = db.getRef("refs/foo/bar");
		assertEquals("refs/foo/bar"
		assertEquals(masterId
	}

