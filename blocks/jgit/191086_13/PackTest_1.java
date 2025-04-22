	@Test
	public void testObjectSize() throws Exception {
		byte[] data = getRng().nextBytes(300);
		RevBlob aBlob = tr.blob(data);
		RevCommit aCommit = tr.branch("master").commit().add("A"
		repo.getConfig().setInt(CONFIG_PACK_SECTION
		tr.packAndPrune();

		List<Pack> packs = repo.getObjectDatabase().getPacks().stream().collect(Collectors.toList());
		assertEquals(1
		assertEquals(300
		assertEquals(300
		assertEquals(-1
		assertEquals(168
		assertEquals(-1
				ObjectId.fromString("1111111111111111111111111111111111111111")));
		assertEquals(-1
				ObjectId.fromString("1111111111111111111111111111111111111111")));
	}

