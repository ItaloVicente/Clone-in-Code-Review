		recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + commit.toObjectId().getName() + "\n",
			"no-progress\n",
			"done\n",
			PacketLineIn.END);
