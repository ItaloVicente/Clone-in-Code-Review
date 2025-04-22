		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"deepen-not twotag\n",
			"want " + four.toObjectId().getName() + "\n",
			"done\n",
			PacketLineIn.END);
