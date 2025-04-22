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

	@Test
	public void testLongPreamble() throws Exception {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < 1024; i++) {
			b.append('a');
		}
		String line = b.toString();
		String[] lines = new String[60];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = line;
		}
		server.setPreamble(lines);
		cloneWith(
						+ "/doesntmatter"
				defaultCloneDir
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test (expected = TransportException.class)
	public void testHugePreamble() throws Exception {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < 1024; i++) {
			b.append('a');
		}
		String line = b.toString();
		String[] lines = new String[70];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = line;
		}
		server.setPreamble(lines);
		cloneWith(
						+ "/doesntmatter"
				defaultCloneDir
				"IdentityFile " + privateKey1.getAbsolutePath());
	}
