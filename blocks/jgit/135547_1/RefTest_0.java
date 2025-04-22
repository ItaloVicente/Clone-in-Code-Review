
	@Test
	public void testResolveTipSha1() throws IOException {
		ObjectId masterId = db.resolve("refs/heads/master");
		Set<Ref> resolved = db.getRefDatabase().resolveTipSha1(masterId);

		assertEquals(resolved.size()
		checkContainsRef(resolved
		checkContainsRef(resolved

		assertEquals(db.getRefDatabase()
				.resolveTipSha1(ObjectId.zeroId()).size()
	}
