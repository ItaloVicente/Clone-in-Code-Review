	@Test
	public void testPreamble() throws Exception {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < 257; i++) {
			b.append('a');
		}
		server.setPreamble("A line with a \000 NUL"
				"A long line: " + b.toString());
		cloneWith(
						+ "/doesntmatter"
				defaultCloneDir
				"IdentityFile " + privateKey1.getAbsolutePath());
	}
