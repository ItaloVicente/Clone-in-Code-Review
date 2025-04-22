	@Test
	public void testCustomObjectReader() throws Exception {
		String refName = "refs/heads/blob";
		String data = "unflushed data";
		ObjectId id;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (Repository repo = new InMemoryRepository(
					new DfsRepositoryDescription("repo"));
				ObjectInserter ins = repo.newObjectInserter();
				ObjectReader or = ins.newReader()) {
			id = ins.insert(OBJ_BLOB
			BundleWriter bw = new BundleWriter(or);
			bw.include(refName
			bw.writeBundle(NullProgressMonitor.INSTANCE
			assertNull(repo.exactRef(refName));
			try {
				repo.open(id
				fail("We should not be able to open the unflushed blob");
			} catch (MissingObjectException e) {
			}
		}

		try (Repository repo = new InMemoryRepository(
					new DfsRepositoryDescription("copy"))) {
			fetchFromBundle(repo
			Ref ref = repo.exactRef(refName);
			assertNotNull(ref);
			assertEquals(id
			assertEquals(data
		}
	}

