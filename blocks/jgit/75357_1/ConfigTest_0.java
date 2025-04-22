	public void testIncludeValuePathWithTilde()
			throws ConfigInvalidException

		FS.DETECTED.setUserHome(tmp.getRoot());

		File config = tmp.newFile("config");
		Files.write(config.toPath()

		Config parsed = parse("[include]\npath=~/config\n");
		assertTrue(parsed.getBoolean("foo"

		FS.DETECTED.setUserHome(null);
		parsed = parse("[include]\npath=~/config\n");
		assertFalse(parsed.getBoolean("foo"
