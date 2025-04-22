	@Test
	public void testRedirectedClone() throws Exception {
		String redirectPath = "/redirect/"
				+ remoteURI.getPath().substring("/git/".length());
		URIish redirectURI = remoteURI.setPath(redirectPath);

		TestRepository dst = createTestRepository();
		Transport t = Transport.open(dst.getRepository()
		try {
			t.fetch(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}
		assertEquals(B
	}

