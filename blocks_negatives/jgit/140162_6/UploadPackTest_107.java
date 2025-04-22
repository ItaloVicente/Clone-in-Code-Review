		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want-ref refs/heads/one\n",
			"have " + one.toObjectId().getName(),
			"done\n",
			PacketLineIn.END);
