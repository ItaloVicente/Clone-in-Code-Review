	public void testFetch_FewLocalCommits() throws Exception {
		TestRepository dst = createTestRepository();
		Transport t = Transport.open(dst.getRepository()
		try {
			t.fetch(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}
		assertEquals(B
		List<AccessEvent> cloneRequests = getRequests();

		TestRepository.BranchBuilder b = dst.branch(master);
		for (int i = 0; i < 4; i++)

		b = new TestRepository(remoteRepository).branch(master);
		RevCommit Z = b.commit().message("Z").create();

		t = Transport.open(dst.getRepository()
		try {
			t.fetch(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}
		assertEquals(Z

		List<AccessEvent> requests = getRequests();
		requests.removeAll(cloneRequests);
		assertEquals(2

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testFetch_TooManyLocalCommits() throws Exception {
