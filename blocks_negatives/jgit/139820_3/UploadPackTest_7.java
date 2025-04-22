		try {
			uploadPackV2(
				"command=fetch\n",
				PacketLineIn.DELIM,
				"want-ref refs/heads/one\n",
				"done\n",
				PacketLineIn.END);
		} catch (PackProtocolException e) {
			assertThat(
				e.getMessage(),
