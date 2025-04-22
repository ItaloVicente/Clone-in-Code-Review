		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + child.toObjectId().getName() + "\n",
			"deepen 1\n",
			PacketLineIn.END);
