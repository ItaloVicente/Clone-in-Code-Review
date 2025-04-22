		Config parsed = parse("[include]\npath=" + notFound + "\n");
		assertEquals(1
		assertEquals(notFound
	}

	@Test
	public void testIncludeValuePathWithTilde() throws ConfigInvalidException {
		String notSupported = "~/someFile";
		Config parsed = parse("[include]\npath=" + notSupported + "\n");
		assertEquals(1
		assertEquals(notSupported
	}

	@Test
	public void testIncludeValuePathRelative() throws ConfigInvalidException {
		String notSupported = "someRelativeFile";
		Config parsed = parse("[include]\npath=" + notSupported + "\n");
		assertEquals(1
		assertEquals(notSupported
