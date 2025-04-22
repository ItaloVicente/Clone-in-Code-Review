		info = requests.get(2);
		assertEquals("GET", info.getMethod());
		assertEquals(join(authURI, "info/refs"), info.getPath());
		assertEquals(1, info.getParameters().size());
		assertEquals("git-upload-pack", info.getParameter("service"));
		assertEquals(200, info.getStatus());
		assertEquals("application/x-git-upload-pack-advertisement",
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip", info.getResponseHeader(HDR_CONTENT_ENCODING));
