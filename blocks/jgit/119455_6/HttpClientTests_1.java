
	@Test
	public void testV2HttpFirstResponse() throws Exception {
		remoteRepository.getRepository().getConfig().setInt(
				"protocol"

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

		String s;
		while ((s = pckIn.readString()) != PacketLineIn.END) {
			assertTrue(!s.isEmpty());
		}
	}

	@Test
	public void testV2HttpSubsequentResponse() throws Exception {
		remoteRepository.getRepository().getConfig().setInt(
				"protocol"

		JDKHttpConnectionFactory f = new JDKHttpConnectionFactory();
		String url = smartAuthNoneURI.toString() + "/git-upload-pack";
		HttpConnection c = f.create(new URL(url));
		c.setRequestMethod("POST");
		c.setRequestProperty("Content-Type"
		c.setRequestProperty("Git-Protocol"
		c.setDoOutput(true);
		c.connect();


		OutputStream os = c.getOutputStream();
		PacketLineOut pckOut = new PacketLineOut(os);
		pckOut.writeString("command=ls-refs");
		pckOut.writeDelim();
		pckOut.end();
		os.close();

		PacketLineIn pckIn = new PacketLineIn(c.getInputStream());

		String s;
		while ((s = pckIn.readString()) != PacketLineIn.END) {
			assertTrue(s.matches("[0-9a-f]{40} [A-Za-z/]*"));
		}

		assertThat(c.getResponseCode()
	}
