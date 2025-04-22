	@Test
	public void test_repack() throws Exception {
		Map<String

				"^" + v1_0.getObject().name() + "\n");
		all = refdir.getRefs(RefDatabase.ALL);

		assertEquals(4
		assertEquals(Storage.LOOSE
		assertEquals(Storage.PACKED
		assertEquals(A.getId()
		assertEquals(Storage.PACKED
		assertEquals(Storage.PACKED

		repo.update("refs/heads/master"
		RevTag v0_1 = repo.tag("v0.1"
		repo.update("refs/tags/v0.1"

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(5
		assertEquals(Storage.LOOSE
		assertEquals(Storage.LOOSE
				.getStorage());
		assertEquals(B.getId()
		assertEquals(Storage.PACKED
		assertEquals(Storage.PACKED
		assertEquals(Storage.LOOSE
		assertEquals(v0_1.getId()

		all = refdir.getRefs(RefDatabase.ALL);
		refdir.pack(all.values().toArray(new String[all.size()]));

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(5
		assertEquals(Storage.LOOSE
		assertEquals(Storage.PACKED
		assertEquals(B.getId()
		assertEquals(Storage.PACKED
		assertEquals(Storage.PACKED
		assertEquals(Storage.PACKED
		assertEquals(v0_1.getId()
	}

