		ByteArrayInputStream recvStream = uploadPackV2(
			"command=ls-refs\n",
			PacketLineIn.delimiter(),
			"ref-prefix refs/heads/maste",
			"ref-prefix r",
				PacketLineIn.end());
