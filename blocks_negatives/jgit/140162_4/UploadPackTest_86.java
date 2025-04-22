		recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + commit.toObjectId().getName() + "\n",
			"include-tag\n",
			"done\n",
			PacketLineIn.END);
