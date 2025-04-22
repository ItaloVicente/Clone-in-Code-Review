		for (int i = 2; i < requests.size(); i++) {
			AccessEvent service = requests.get(i);
			assertEquals("POST", service.getMethod());
			assertEquals(join(remoteURI, "git-upload-pack"), service.getPath());
			assertEquals(0, service.getParameters().size());
			assertNotNull("has content-length",
					service.getRequestHeader(HDR_CONTENT_LENGTH));
			assertNull("not chunked",
					service.getRequestHeader(HDR_TRANSFER_ENCODING));
			assertEquals(200, service.getStatus());
			assertEquals("application/x-git-upload-pack-result",
					service.getResponseHeader(HDR_CONTENT_TYPE));
		}
