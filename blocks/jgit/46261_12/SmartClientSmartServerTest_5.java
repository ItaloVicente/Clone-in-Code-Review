		AccessEvent service = requests.get(n++);
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
	public void testInitialClone_Redirect301Small() throws Exception {
		initialClone_Redirect(1
	}

	@Test
	public void testInitialClone_Redirect302Small() throws Exception {
		initialClone_Redirect(1
	}
