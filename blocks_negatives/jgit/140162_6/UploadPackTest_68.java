		ByteArrayInputStream recvStream = uploadPackV2(
			"command=ls-refs\n",
			PacketLineIn.DELIM,
			"ref-prefix refs/heads/maste",
			"ref-prefix refs/heads/other",
			PacketLineIn.END);
