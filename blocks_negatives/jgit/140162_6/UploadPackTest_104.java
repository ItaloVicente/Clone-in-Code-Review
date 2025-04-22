		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want-ref refs/heads/one\n",
			"want-ref refs/heads/two\n",
			"done\n",
			PacketLineIn.END);
