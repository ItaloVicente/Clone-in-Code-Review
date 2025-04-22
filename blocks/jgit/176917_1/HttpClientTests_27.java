	public void testHttpClientWantsV2AndServerNotConfigured() throws Exception {
		String url = smartAuthNoneURI.toString() + "/info/refs?service=git-upload-pack";
		HttpConnection c = HttpTransport.getConnectionFactory()
				.create(new URL(url));
		c.setRequestMethod("GET");
		c.setRequestProperty("Git-Protocol"
		assertEquals(200

		PacketLineIn pckIn = new PacketLineIn(c.getInputStream());
		assertThat(pckIn.readString()
	}

	@Test
	public void testHttpServerConfiguredToV0() throws Exception {
		remoteRepository.getRepository().getConfig().setInt(
			"protocol"
