		for (int i = 2; i < requests.size(); i++) {
			AccessEvent service = requests.get(i);
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
