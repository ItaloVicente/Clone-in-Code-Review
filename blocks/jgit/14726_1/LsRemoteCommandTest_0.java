	@Test
	public void testLsRemoteWithoutLocalRepository() throws Exception {
		Collection<Ref> refs = Git.lsRemoteRepository().setRemote(uri).setHeads(true).call();
		assertNotNull(refs);
		assertEquals(2
	}

