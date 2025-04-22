
	@Test
	public void testV2HttpFirstResponseUnadvertised() throws Exception {
		remoteRepository.getRepository().getConfig().setInt(
				"protocol"
		remoteRepository.getRepository().getConfig().setBoolean(
				"uploadpack"

		JDKHttpConnectionFactory f = new JDKHttpConnectionFactory();
		String url = smartAuthNoneURI.toString() + "/info/refs?service=git-upload-pack";
		HttpConnection c = f.create(new URL(url));
		c.setRequestMethod("GET");
		c.setRequestProperty("Git-Protocol"
		c.connect();
		assertThat(c.getResponseCode()

		PacketLineIn pckIn = new PacketLineIn(c.getInputStream());
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
	}

	@Test
	public void testV2HttpSubsequentResponseUnadvertised() throws Exception {
		remoteRepository.getRepository().getConfig().setBoolean(
				"uploadpack"
		testV2HttpSubsequentResponse();
	}
