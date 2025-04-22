	@Test
	public void commitToUnbornHead() throws Exception {
		repo.updateRef("HEAD").link("refs/heads/master");
		RevCommit root = tr.branch("HEAD").commit().create();
		Ref ref = repo.getRef(Constants.HEAD);
		assertEquals(root
		assertTrue(ref.isSymbolic());
		assertEquals("refs/heads/master"
	}

