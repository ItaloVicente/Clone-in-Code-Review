
	@Test
	public void testManifestParserWithMissingFetchOnRemote() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append("foo")
				.append("\" groups=\"a
				.append("<project path=\"bar\" name=\"").append("bar")
				.append("\" groups=\"notdefault\" />")
				.append("<project path=\"foo/a\" name=\"").append("a")
				.append("\" groups=\"a\" />")
				.append("<project path=\"b\" name=\"").append("b")
				.append("\" groups=\"b\" />").append("</manifest>");

		ManifestParser parser = new ManifestParser(null
				baseUrl
		try {
			parser.read(new ByteArrayInputStream(
					xmlContent.toString().getBytes(UTF_8)));
		} catch (IOException e) {
			assertTrue(e.getCause() instanceof SAXException);
			assertTrue(e.getCause().getMessage()
					.contains("is missing fetch attribute"));
		}
	}
