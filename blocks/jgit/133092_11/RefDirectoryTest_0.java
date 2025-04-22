	@Test(expected = UnsupportedOperationException.class)
	public void testVersioningNotImplemented_exactRef() throws IOException {
		assertFalse(refdir.hasVersioning());

		Ref ref = refdir.exactRef(HEAD);
		assertNotNull(ref);
	}

	@Test
	public void testVersioningNotImplemented_getRefs() throws Exception {
		assertFalse(refdir.hasVersioning());

		RevCommit C = repo.commit().parent(B).create();
		repo.update("master"
		List<Ref> refs = refdir.getRefs();

		for (Ref ref : refs) {
			try {
				ref.getUpdateIndex();
				fail("FS doesn't implement ref versioning");
			} catch (UnsupportedOperationException e) {
			}
		}
	}

