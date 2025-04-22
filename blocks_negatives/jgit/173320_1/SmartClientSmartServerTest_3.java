		AccessEvent service = requests.get(3);
		assertEquals("POST", service.getMethod());
		assertEquals(join(authURI, "git-upload-pack"), service.getPath());
		assertEquals(0, service.getParameters().size());
		assertNotNull("has content-length",
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked",
				service.getRequestHeader(HDR_TRANSFER_ENCODING));
