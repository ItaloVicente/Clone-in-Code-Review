	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setTranscoder(new WhalinV1Transcoder());
	}

	@Override
	public void testByteArray() throws Exception {
		byte[] a={'a', 'b', 'c'};

		CachedData cd=getTranscoder().encode(a);
		byte[] decoded=(byte[])getTranscoder().decode(cd);
		assertNotNull(decoded);
		assertTrue(Arrays.equals(a, decoded));
	}

	@Override
	protected int getStringFlags() {
		return 0;
	}

