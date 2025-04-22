	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testEmptyBundleFails() throws Exception {
		Repository newRepo = createBareRepository();
		thrown.expect(TransportException.class);
		fetchFromBundle(newRepo
	}

	@Test
	public void testNonBundleFails() throws Exception {
		Repository newRepo = createBareRepository();
		thrown.expect(TransportException.class);
		fetchFromBundle(newRepo
	}

	@Test
	public void testGarbageBundleFails() throws Exception {
		Repository newRepo = createBareRepository();
		thrown.expect(TransportException.class);
		fetchFromBundle(newRepo
				(TransportBundle.V2_BUNDLE_SIGNATURE + '\n' + "Garbage")
						.getBytes(UTF_8));
	}

