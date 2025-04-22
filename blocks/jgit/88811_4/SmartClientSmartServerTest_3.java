	@Test
	public void testInitialClone_RedirectSmall() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
		}

		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> requests = getRequests();
		assertEquals(4

		AccessEvent firstRedirect = requests.get(0);
		assertEquals(301

		AccessEvent info = requests.get(1);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent secondRedirect = requests.get(2);
		assertEquals(301

		AccessEvent service = requests.get(3);
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

