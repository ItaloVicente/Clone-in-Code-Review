		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want-ref refs/heads/branch1\n",
			"deepen 1\n",
			"done\n",
			PacketLineIn.END);
