		if (!enableProtocolV2) {
			assertEquals("gzip"
		} else {
			AccessEvent lsRefs = requests.get(requestNumber++);
			assertEquals("POST"
			assertEquals(join(authURI
			assertEquals(0
			assertNotNull("has content-length"
					lsRefs.getRequestHeader(HDR_CONTENT_LENGTH));
			assertNull("not chunked"
					lsRefs.getRequestHeader(HDR_TRANSFER_ENCODING));
			assertEquals("version=2"
			assertEquals(200
			assertEquals("application/x-git-upload-pack-result"
					lsRefs.getResponseHeader(HDR_CONTENT_TYPE));
		}
