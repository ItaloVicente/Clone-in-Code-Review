	@Test
	public void testV2LsRefsServerOptions() throws Exception {
		String[] lines = { "command=ls-refs\n"
				"server-option=one\n"
				PacketLineIn.DELIM
				PacketLineIn.END };

		TestV2Hook testHook = new TestV2Hook();
		uploadPackV2Setup(null

		LsRefsV2Request req = testHook.lsRefsRequest;
		assertEquals(2
		assertThat(req.getServerOptions()
	}

