	@Test
	public void testRemoveProject() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"foo\" />")
				.append("<project path=\"bar\" name=\"bar\" />")
				.append("<remove-project name=\"foo\" />")
				.append("<project path=\"foo\" name=\"baz\" />")
				.append("</manifest>");

		ManifestParser parser = new ManifestParser(null
		parser.read(new ByteArrayInputStream(
				xmlContent.toString().getBytes(CHARSET)));

		assertEquals(Stream.of("bar"
				parser.getProjects().stream().map(RepoProject::getName)
						.collect(Collectors.toSet()));
	}

