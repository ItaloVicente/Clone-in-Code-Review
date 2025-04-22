	@Test
	public void testV2FetchServerOptions() throws Exception {
		String[] lines = { "command=fetch\n"
				"server-option=two\n"
				PacketLineIn.END };

		TestV2Hook testHook = new TestV2Hook();
		uploadPackV2Setup(null

		FetchV2Request req = testHook.fetchRequest;
		assertNotNull(req);
		assertEquals(2
		assertThat(req.getServerOptions()
	}

