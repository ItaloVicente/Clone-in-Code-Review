
	@Test
	public void testResolveTipSha1() throws IOException {
		ObjectId masterId = db.resolve("refs/heads/master");
		Set<Ref> resolved = db.getRefDatabase().getTipsWithSha1(masterId);

		assertEquals(2
		checkContainsRef(resolved
		checkContainsRef(resolved

		assertEquals(db.getRefDatabase()
				.getTipsWithSha1(ObjectId.zeroId()).size()
	}
