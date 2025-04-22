	@Test
	public void testRemoveProject() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		Set<String> results = new HashSet<>();
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
				baseUrl
		parser.read(new ByteArrayInputStream(
				xmlContent.toString().getBytes(CHARSET)));

		results.clear();
		results.add("bar");
		results.add("baz");
		for (RepoProject proj : parser.getProjects()) {
			assertTrue("Unexpected project is included: " + proj.getName()
					results.contains(proj.getName()));
			results.remove(proj.getName());
		}
		assertTrue(
				"Expected projects are not included"
				results.isEmpty());
	}

