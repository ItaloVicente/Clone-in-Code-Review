	@Test(expected = UnsupportedOperationException.class)
	public void testUpdateIndexNotImplemented() throws IOException {
		Ref exactRef = refdir.exactRef(HEAD);
		assertNotNull(exactRef);
	}

	@Test
	public void testUpdateIndexNotImplemented2() throws Exception {
		RevCommit C = repo.commit().parent(B).create();
		repo.update("master"
		List<Ref> refs = refdir.getRefs();

		for (Ref ref: refs) {
			try {
				ref.getVersion();
				fail("FS doesn't implement update index");
			} catch (UnsupportedOperationException e) {
			}
		}
	}

