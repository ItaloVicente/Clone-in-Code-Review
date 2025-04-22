		if (!enableProtocolV2) {
			assertEquals("gzip", info.getResponseHeader(HDR_CONTENT_ENCODING));
		} else {
			AccessEvent lsRefs = requests.get(1);
			assertEquals("POST", lsRefs.getMethod());
			assertEquals(join(remoteURI, "git-upload-pack"), lsRefs.getPath());
			assertEquals(0, lsRefs.getParameters().size());
			assertNotNull("has content-length",
					lsRefs.getRequestHeader(HDR_CONTENT_LENGTH));
			assertNull("not chunked",
					lsRefs.getRequestHeader(HDR_TRANSFER_ENCODING));
			assertEquals("version=2", lsRefs.getRequestHeader("Git-Protocol"));
			assertEquals(200, lsRefs.getStatus());
			assertEquals("application/x-git-upload-pack-result",
					lsRefs.getResponseHeader(HDR_CONTENT_TYPE));
		}
