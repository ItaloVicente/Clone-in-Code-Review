	private void assertFetchRequests(List<AccessEvent> requests
		AccessEvent info = requests.get(index++);
		assertEquals("GET"
		assertEquals(join(authURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		if (!enableProtocolV2) {
			assertEquals("gzip"
		}

		for (int i = index; i < requests.size(); i++) {
			AccessEvent service = requests.get(i);
			assertEquals("POST"
			assertEquals(join(authURI
			assertEquals(0
			assertNotNull("has content-length"
					service.getRequestHeader(HDR_CONTENT_LENGTH));
			assertNull("not chunked"
					service.getRequestHeader(HDR_TRANSFER_ENCODING));

			assertEquals(200
			assertEquals("application/x-git-upload-pack-result"
					service.getResponseHeader(HDR_CONTENT_TYPE));
		}
	}

