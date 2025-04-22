		try {
			uploadPackV2(
				"command=fetch\n",
				PacketLineIn.delimiter(),
				"want-ref refs/heads/one\n",
				"done\n",
					PacketLineIn.end());
		} catch (PackProtocolException e) {
			assertThat(
				e.getMessage(),
				containsString("unexpected want-ref refs/heads/one"));
			return;
		}
		fail("expected PackProtocolException");
