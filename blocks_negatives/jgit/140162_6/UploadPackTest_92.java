		recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + child.toObjectId().getName() + "\n",
			"done\n",
			PacketLineIn.END);
