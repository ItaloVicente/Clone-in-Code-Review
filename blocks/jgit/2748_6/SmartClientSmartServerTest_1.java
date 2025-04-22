	@Test
	public void testListRemote_BadName() throws IOException
		Repository dst = createBareRepository();
		URIish uri = new URIish(this.remoteURI.toString() + ".invalid");
		Transport t = Transport.open(dst
		try {
			try {
				t.openFetch();
				fail("fetch connection opened");
			} catch (RemoteRepositoryException notFound) {
				assertEquals(uri + ": Git repository not found"
						notFound.getMessage());
			}
		} finally {
			t.close();
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(1

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(uri
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
	}

