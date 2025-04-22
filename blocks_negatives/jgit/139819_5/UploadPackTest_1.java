		try {
			uploadPackV2(
				"command=fetch\n",
				PacketLineIn.delimiter(),
				"want-ref refs/heads/one\n",
				"want-ref refs/heads/nonExistentRef\n",
				"done\n",
					PacketLineIn.end());
		} catch (PackProtocolException e) {
			assertThat(
				e.getMessage(),
