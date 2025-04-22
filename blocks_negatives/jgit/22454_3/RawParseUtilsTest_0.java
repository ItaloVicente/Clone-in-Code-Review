		try {
			RawParseUtils.parseEncoding(Constants.encodeASCII("encoding xyz\n"));
			fail("should throw an UnsupportedCharsetException: xyz");
		} catch (UnsupportedCharsetException e) {
			assertEquals("xyz", e.getMessage());
		}
